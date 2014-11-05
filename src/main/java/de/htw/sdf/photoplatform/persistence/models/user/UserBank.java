/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.persistence.models.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.htw.sdf.photoplatform.persistence.AbstractBaseAuditEntity;

/**
 * Created by Sergej Meister
 */
@Entity
@Table(name = "SYS_USERBANK")
public class UserBank extends AbstractBaseAuditEntity {
    private static final long serialVersionUID = 1179519484452211533L;

    @OneToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @Column(name = "RECEIVER")
    private String receiver;

    @Column(name = "IBAN")
    private String iban;

    @Column(name = "BIC")
    private String bic;

    /**
     * Default Constructor.
     */
    public UserBank()
    {
        super();
    }

    /**
     * Returns user.
     * 
     * @return user
     */
    public User getUser()
    {
        return user;
    }

    /**
     * Set user.
     * 
     * @param user
     */
    public void setUser(User user)
    {
        this.user = user;
    }

    /**
     * Returns receiver.
     * 
     * @return receiver
     */
    public String getReceiver()
    {
        return receiver;
    }

    /**
     * Set receiver.
     * 
     * @param receiver
     */
    public void setReceiver(String receiver)
    {
        this.receiver = receiver;
    }

    /**
     * Returns iban.
     * 
     * @return iban
     */
    public String getIban()
    {
        return iban;
    }

    /**
     * Set iban.
     * 
     * @param iban
     */
    public void setIban(String iban)
    {
        this.iban = iban;
    }

    /**
     * Returns bic.
     * 
     * @return ic
     */
    public String getBic()
    {
        return bic;
    }

    /**
     * Set bic.
     * 
     * @param bic
     */
    public void setBic(String bic)
    {
        this.bic = bic;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return "ID" + this.id + " USER: " + this.user.getId() + " IBAN: " + this.iban + " BIC: "
                + this.bic;
    }
}
