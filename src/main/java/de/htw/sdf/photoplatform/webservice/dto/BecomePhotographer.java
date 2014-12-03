/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.webservice.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Data Transfer Object.
 *
 * @author <a href="mailto:s0531603@htw-berlin.de">Daniil Tomilow</a>
 */
public class BecomePhotographer {

    @NotEmpty
    private String company;

    // optional
    private String homepage;

    @NotEmpty
    private String iban;

    @NotEmpty
    private String swift;

    @NotEmpty
    private String phone;

    @NotEmpty
    private String paypalID;

    /**
     * get Paypal ID
     *
     * @return paypalID
     */
    public String getPaypalID() {
        return paypalID;
    }

    /**
     * set the Paypal ID
     *
     * @param paypalID
     */
    public void setPaypalID(String paypalID) {
        this.paypalID = paypalID;
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
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
