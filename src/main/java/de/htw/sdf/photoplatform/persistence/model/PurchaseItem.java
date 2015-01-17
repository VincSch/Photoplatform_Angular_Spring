/**
 *
 */
package de.htw.sdf.photoplatform.persistence.model;

import de.htw.sdf.photoplatform.persistence.AbstractBaseAuditEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Entity class for a res_purchases representing the corresponding database table.
 *
 * PurchaseItem represent an image in the shopping cart.
 * That mean, for every image in shoppingCart will be created a PurchaseItem.
 * Not good, but is simple to implement.
 *
 * @author Sergej Meister
 */
@Entity
@Table(name = "RES_PURCHASES", uniqueConstraints = @UniqueConstraint(columnNames = {"USER_ID", "IMAGE_ID"}))
public class PurchaseItem extends AbstractBaseAuditEntity {

    /**
     * User.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "IMAGE_ID", referencedColumnName = "ID")
    private Image image;

    @Column(name = "PURCHASED", columnDefinition = "boolean default false")
    private Boolean purchased;
    
    @Column(name = "PAYMENTID")
    private String paymentId;


    /**
     * Returns album owner.
     *
     * @return user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets album owner.
     *
     * @param user owner.
     */
    public void setUser(User user) {
        this.user = user;
    }


    /**
     * Returns images.
     *
     * @return return image.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Set image.
     *
     * @param image image.
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Is image purchased!
     * <p/>
     * Default value false!
     *
     * @return true, if purchased!
     */
    public Boolean isPurchased() {
        return purchased;
    }

    /**
     * Set purchased value!
     *
     * @param purchased
     */
    public void setPurchased(Boolean purchased) {
        this.purchased = purchased;
    }
    
    public String getPaymentId() {
    	return this.paymentId;
    }
    
    public void setPaymentId(String paymentId) {
    	this.paymentId = paymentId;
    }
}
