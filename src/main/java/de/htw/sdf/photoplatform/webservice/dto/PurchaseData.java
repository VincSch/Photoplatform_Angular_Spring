/**
 *
 */
package de.htw.sdf.photoplatform.webservice.dto;

import de.htw.sdf.photoplatform.persistence.model.PurchaseItem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

/**
 * Data transfer object represent shopping cart data.
 *
 * @author Sergej Meister
 */
public class PurchaseData extends ResponseMessageData implements Serializable {

    public static final NumberFormat NUMBER_FORMAT = NumberFormat.getCurrencyInstance(Locale.GERMANY);
    static {
        NUMBER_FORMAT.setMinimumFractionDigits(2);
    }

    private List<PurchaseItemData> purchaseItems;
    private int totalItems;
    private BigDecimal totalPrice;


    /**
     * Default Constructor.
     */
    public PurchaseData() {
        this.purchaseItems = new ArrayList<>();
        this.totalItems = this.purchaseItems.size();
        this.totalPrice = new BigDecimal(0.0);
    }

    /**
     * Constructor with converter list of purchaseItems in list of PurchaseItemData.
     *
     * @param items      list of domain object <code>PurchaseItem</code>
     * @param totalPrice calculated price.
     */
    public PurchaseData(List<PurchaseItem> items, BigDecimal totalPrice) {
        this.purchaseItems = new ArrayList<>();
        for (PurchaseItem item : items) {
            PurchaseItemData purchaseItemData = new PurchaseItemData(item);
            purchaseItems.add(purchaseItemData);
        }
        this.totalItems = this.purchaseItems.size();
        this.totalPrice = totalPrice;
    }

    /**
     * Returns list of image items in shopping cart.
     *
     * @return list of image items.
     */
    public List<PurchaseItemData> getPurchaseItems() {
        return purchaseItems;
    }

    /**
     * Sets list of image items in shopping cart.
     *
     * @param purchaseItems list of image items.
     */
    public void setPurchaseItems(List<PurchaseItemData> purchaseItems) {
        this.purchaseItems = purchaseItems;
    }

    /**
     * Returns count of image item in shopping cart.
     *
     * @return length of list <code>purchaseItems</code>
     */
    public int getTotalItems() {
        return totalItems;
    }

    /**
     * Returns calculated price of all images in the list of purchaseItems.
     *
     * @return calculated price of images.
     */
    public String getTotalPrice() {
        return NUMBER_FORMAT.format(totalPrice);
    }
}
