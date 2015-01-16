/**
 *
 */
package de.htw.sdf.photoplatform.webservice.dto;

import de.htw.sdf.photoplatform.persistence.model.PhotographerData;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserRole;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Data transfer object to get and update user data. Represents the domain
 * object User.
 *
 * @author Sergej Meister
 */
public class UserData implements Serializable {

    private Long id;

    @Email
    private String email;
    private String firstName;
    private String lastName;

    private boolean banned;
    private boolean enabled;
    private boolean admin;

    private String phone;
    private String company;
    private String homepage;
    private String iban;
    private String swift;
    private String paypalID;
    private int totalItems;

    private String secToken;
    private List<String> roles;


    /**
     * Default constructor
     */
    public UserData(){}

    /**
     * Public constructor.
     *
     * @param user the user to return
     */
    public UserData(final User user) {
        this.id = user.getId();
        this.email = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();

        this.banned = !user.isAccountNonLocked();
        this.enabled = user.isEnabled();
        this.admin = user.isAdmin();

        PhotographerData data = user.getPhotographerData();
        if (data != null) {
            this.company = data.getCompany();
            this.phone = data.getPhone();
            this.homepage = data.getHomepage();
            this.paypalID = data.getPaypalID();
            this.swift = data.getSwift();
            this.iban = data.getIban();
        } else {
            this.company = "";
            this.phone = "";
            this.homepage = "";
            this.paypalID = "";
            this.swift = "";
            this.iban = "";
        }

        this.secToken = user.getSecToken();
        List<String> roles = new ArrayList<>();
        for (UserRole userRole : user.getUserRoles()) {
            roles.add(userRole.getRole().getName());
        }

        this.roles = roles;
    }

    /**
     * Returns user id.
     *
     * @return user id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns user email.
     *
     * @return email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Is user banned.
     *
     * @return true if banned.
     */
    public Boolean isBanned() {
        return banned;
    }

    /**
     * Sets value user banned.
     *
     * @param banned is banned.
     */
    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    /**
     * Is admin.
     *
     * @return true if user has role admin.
     */
    public Boolean isAdmin() {
        return admin;
    }

    /**
     * Set admin value.
     *
     * @param admin isAdmin.
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    /**
     * Returns is user enabled.
     *
     * @return true if enabled.
     */
    public Boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets value to enabled.
     *
     * @param enabled true if enabled.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the sec token
     */
    public String getSecToken() {
        return secToken;
    }

    /**
     * @param secToken the sec token to set
     */
    public void setSecToken(String secToken) {
        this.secToken = secToken;
    }

    /**
     * get roles
     *
     * @return list of roles
     */
    public List<String> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles
     */
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    /**
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the swift
     */
    public String getSwift() {
        return swift;
    }

    /**
     * @param swift
     */
    public void setSwift(String swift) {
        this.swift = swift;
    }

    /**
     * @return the iban
     */
    public String getIban() {
        return iban;
    }

    /**
     * @param iban
     */
    public void setIban(String iban) {
        this.iban = iban;
    }

    /**
     * @return
     */
    public String getHomepage() {
        return homepage;
    }

    /**
     * @param homepage
     */
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    /**
     * @return the company
     */
    public String getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return paypalID
     */
    public String getPaypalID() { return paypalID; }

    /**
     * @param paypalID
     */
    public void setPaypalID(String paypalID) { this.paypalID = paypalID; }

    /**
     * @return the totalItems
     */
    public int getTotalItems() {
        return totalItems;
    }

    /**
     * @param totalItems the totalItems to set
     */
    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
}
