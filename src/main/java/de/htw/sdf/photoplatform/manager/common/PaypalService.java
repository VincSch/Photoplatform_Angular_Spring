package de.htw.sdf.photoplatform.manager.common;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.paypal.api.payments.*;
import com.paypal.core.ConfigManager;
import com.paypal.core.rest.OAuthTokenCredential;
import com.paypal.core.rest.PayPalRESTException;
import com.paypal.core.rest.PayPalResource;

@Service
public class PaypalService extends DAOReferenceCollector {
    private OAuthTokenCredential OAuthToken;

    public PaypalService() throws PayPalRESTException {
    	PayPalResource.initializeToDefault();
    	Logger Log = Logger.getLogger("PaypalService");
    	Log.info("ClientID: " + PayPalResource.getClientID());
    	Log.info("Secret: " + PayPalResource.getClientSecret());
    	OAuthToken = new OAuthTokenCredential(PayPalResource.getClientID(), PayPalResource.getClientSecret());
    }
    
    public String getOAuthToken() throws PayPalRESTException {
    	return OAuthToken.getAccessToken();
    }
}
