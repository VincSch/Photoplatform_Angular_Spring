/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.persistence.model;

import de.htw.sdf.photoplatform.persistence.AbstractBaseAuditEntity;

import javax.persistence.*;

/**
 * Entity class for a user details representing the corresponding database
 * table.
 */
@Entity
@Table(name = "SYS_USERPROFILE")
public class UserProfile extends AbstractBaseAuditEntity {

    private static final long serialVersionUID = -5889099084305292787L;

    @OneToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "BIRTHDAY")
    private String birthday;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "COMPANY")
    private String company;

    @Column(name = "HOMEPAGE")
    private String homepage;

    /**
     * Constructor with user.
     * @param user user.
     */
    public UserProfile(User user) {
        super();
        this.user = user;
    }

    /**
     * Default constructor.
     */
    public UserProfile() {
        super();
    }

    public User getUser() {
        return user;
    }

    /**
     * Set user.
     *
     * @param user
     *            user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns user firts name.
     *
     * @return first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets user first name.
     *
     * @param firstName
     *            first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns user lastName.
     *
     * @return lastName.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets user lastName.
     *
     * @param lastName
     *            lastName.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns birthday.
     * 
     * @return birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * Set birthday.
     * 
     * @param birthday
     *            birthday
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * Returns address.
     * 
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set address.
     * 
     * @param address
     *            address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns phone.
     * 
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set phone.
     * 
     * @param phone
     *            phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns company.
     * 
     * @return company
     */
    public String getCompany() {
        return company;
    }

    /**
     * Set company.
     * 
     * @param company
     *            company
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * Returns homepage.
     * 
     * @return homepage
     */
    public String getHomepage() {
        return homepage;
    }

    /**
     * Set homepage.
     * 
     * @param homepage
     *            homepage
     */
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ID:" + this.id + " UserID: " + this.user.getId() + " Address: "
                + this.address + " Phone: " + this.phone;
    }
}
