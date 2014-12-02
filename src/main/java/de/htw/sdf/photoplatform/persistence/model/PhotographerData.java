/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.persistence.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Entity class for a user representing the corresponding database table.
 *
 * @author <a href="mailto:s05316@htw-berlin.de">Vincent Schwarzer</a>
 * @author <a href="mailto:s0531603@htw-berlin.de">Daniil Tomilow</a>
 */
@Embeddable
public class PhotographerData {

    @Column(name = "COMPANY")
    private String company;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "HOMEPAGE")
    private String homepage;

    @Column(name = "PAYPALID")
    private String paypalID;

    @Column(name = "IBAN")
    private String iban;

    @Column(name = "SWIFT")
    private String swift;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Collection> collections;

    /**
     * get phone number
     *
     * @return phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone phone number of this user
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * get the company
     *
     * @return the company
     */
    public String getCompany() {
        return company;
    }

    /**
     * set the company
     *
     * @param company company name
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * get homepage
     *
     * @return homepage of this user
     */
    public String getHomepage() {
        return homepage;
    }

    /**
     * set homepage
     *
     * @param homepage homepage of the user
     */
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    /**
     * get the iban
     *
     * @return iban of the user
     */
    public String getIban() {
        return iban;
    }

    /**
     * set the iban
     *
     * @param iban iban to set
     */
    public void setIban(String iban) {
        this.iban = iban;
    }

    /**
     * get the swift
     *
     * @return swift of the user
     */
    public String getSwift() {
        return swift;
    }

    /**
     * set the swift
     *
     * @param swift value
     */
    public void setSwift(String swift) {
        this.swift = swift;
    }

    /**
     * @return the collection
     */
    public List<Collection> getCollections() {
        return collections;
    }

    /**
     * @param collections the collections to set
     */
    public void setCollections(List<Collection> collections) {
        this.collections = collections;
    }

    /**
     * get the Paypal ID
     *
     * @return paypalID
     */
    public String getPaypalID() { return paypalID;}

    /**
     * set the Paypal ID
     *
     * @param paypalID
     */
    public void setPaypalID(String paypalID) { this.paypalID = paypalID;}
}
