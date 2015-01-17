package de.htw.sdf.photoplatform.manager.common;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.elasticsearch.common.mvel2.optimizers.impl.refl.nodes.ThisValueAccessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.paypal.api.payments.*;
import com.paypal.core.ConfigManager;
import com.paypal.core.rest.APIContext;
import com.paypal.core.rest.OAuthTokenCredential;
import com.paypal.core.rest.PayPalRESTException;
import com.paypal.core.rest.PayPalResource;

import de.htw.sdf.photoplatform.common.Messages;
import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.exception.common.ServiceException;
import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.PurchaseItem;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.webservice.Endpoints;

@Service
public class PaypalService extends DAOReferenceCollector {
	
	public class CreatePaymentResult {
		
		private String PaymentID;
		private String RedirectURL;
		
		public CreatePaymentResult(String PaymentID, String RedirectURL)
		{
			this.PaymentID = PaymentID;
			this.RedirectURL = RedirectURL;
		}
		
		public String GetPaymentID()
		{
			return PaymentID;
		}
		
		public String GetRedirectURL()
		{
			return RedirectURL;
		}		
	}
	
	private Logger Log = Logger.getLogger(PaypalService.class.getName());

    @Resource
    private Messages messages;
    
    /*
     * OAuth Token with additional information
     */
    private OAuthTokenCredential OAuthToken;

	/**
	 * Default Constructor
	 */
    public PaypalService() {
    	try {
	    	//Need to be called so ConfigMap is initialized everywhere in the SDK/API, however it still loads the sdk_config file
	    	PayPalResource.initializeToDefault();
	    	Log.info("ClientID: " + PayPalResource.getClientID());
	    	Log.info("Secret: " + PayPalResource.getClientSecret());
    	} catch(PayPalRESTException ex) {
    		Log.error("Paypal Rest API threw an error on initialisation: " + ex.getMessage());
    	}
    }
    
    /*
     *	Generates a new OAuthToken, replacing the old one
     */
    private void generateNewOAuthToken() {
    	OAuthToken = new OAuthTokenCredential(PayPalResource.getClientID(), PayPalResource.getClientSecret());
    }

    /*
     *	Returns a non expired OAuthToken
     *
     *	@return valid OAuth Paypal token
     */
    public String getOAuthToken() throws PayPalRESTException {
    	if((OAuthToken == null) || (OAuthToken.expiresIn() < 0)) {
    		generateNewOAuthToken();
    	}
    	
    	return OAuthToken.getAccessToken();
    }
    
    /*
     * Creates a payment on paypal
     * 
     * @param Items the to be puchased items.
     * @param ApprovedURL The url the user is redirected after the payment was approved
     * @param CancledURL The url the user is redirected after the payment was cancled
     * 
     * @returns the payment id and redirect url
     */
    public CreatePaymentResult CreatePayment(List<Image> Items, String ApprovedURL, String CancledURL) throws AbstractBaseException {
    	// Use nulled Local so String.format will use . as decimal delimiter
    	Locale l = null;
    	BigDecimal Total = new BigDecimal(0.0);
    	
    	//ItemList 
    	List<Item> PaypalItems = new ArrayList<Item>();
    	for (Image Image : Items) {
    		Item item = new Item("1", Image.getName(), String.format(l, "%.2f" ,Image.getPrice()), "EUR");
    		Total = Total.add(Image.getPrice());
    		PaypalItems.add(item);
    	}
    	
    	ItemList itemList = new ItemList();
    	itemList.setItems(PaypalItems);

    	//Amount
    	Amount amount = new Amount("EUR", String.format(l, "%.2f" ,Total));
    	
    	//Transaction
    	Transaction transaction = new Transaction();
    	transaction.setAmount(amount);
    	transaction.setItemList(itemList);
    	transaction.setDescription(messages.getMessage("Paypal.transaction.description"));

		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);
		
    	//Payer
		Payer payer = new Payer("paypal");
    	
    	// Payment
		Payment payment = new Payment("sale", payer);
		payment.setTransactions(transactions);
		
    	//RedirectURLS
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(CancledURL);
		redirectUrls.setReturnUrl(ApprovedURL);
		payment.setRedirectUrls(redirectUrls);
		
    	// Create
		Payment newPayment;
		try {
			newPayment = payment.create(getOAuthToken());
		} catch(PayPalRESTException ex)
		{
			//Error
    		Log.error("Error while trying to create Paypal Payment: " + ex.getMessage());
    		throw new ServiceException(AbstractBaseException.PAYPAL_REST_ERROR);
		}
		
		String RedirectURL = "";
		
		// Get redirect link to paypal page
		Iterator<Links> links = newPayment.getLinks().iterator();
		while (links.hasNext()) {
			Links link = links.next();
			if (link.getRel().equalsIgnoreCase("approval_url")) {
		    	// return to redirect
				RedirectURL = link.getHref();
			}
		}
		
		if(RedirectURL.isEmpty())
		{
			Log.warn("No paypal redirect link found!");
    		throw new ServiceException(AbstractBaseException.PAYPAL_REST_ERROR);
		}
		
		return new CreatePaymentResult(newPayment.getId(), RedirectURL);
		
		//return newPayment.toJSON();
    }
    
    /*
     * Executes the payment on paypal, so it is fullfilled
     * 
     * @param PaymentId Payment ID of this payment process.
     * @param PayerID Payer ID of the buyer; provided by the paypal redirect.
     * 
     */
    public void ExecutePayment(String PaymentId, String PayerID) throws AbstractBaseException {
    	Payment payment = new Payment();
    	payment.setId(PaymentId);
    	
    	PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(PayerID);
		
		Payment FullfilledPayment;
		try {
			FullfilledPayment = payment.execute(getOAuthToken(), paymentExecution);
		} catch(PayPalRESTException ex)
		{
			//Error
    		Log.error("Error while trying to execute Paypal Payment: " + ex.getMessage());
    		throw new ServiceException(AbstractBaseException.PAYPAL_REST_ERROR);
		}
		
		//return FullfilledPayment.toJSON();
    }
    
    public String GetPaymentTotal(String PaymentId) throws AbstractBaseException {
    	Payment payment;
		try {
			payment = Payment.get(getOAuthToken(), PaymentId);
		} catch(PayPalRESTException ex)
		{
			//Error
    		Log.error("Error while trying to execute Paypal Payment: " + ex.getMessage());
    		throw new ServiceException(AbstractBaseException.PAYPAL_REST_ERROR);
		}
		
		List<Transaction> transactions = payment.getTransactions();
		if(transactions.size() > 0)
		{
			return transactions.get(0).getAmount().getTotal();
		}
		
		return "0.00";
    }
}
