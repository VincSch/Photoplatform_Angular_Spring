/**
 *
 */
package de.htw.sdf.photoplatform.webservice.dto.response;

import de.htw.sdf.photoplatform.persistence.model.Role;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * Data transfer object to get and update user data.
 * Represents the domain object User.
 *
 * @author Sergej Meister
 */
public class UserData implements Serializable {

    @NotEmpty
    protected Long id;

    protected String username;

    protected String password;

    protected String secToken;

    @Email
    protected String email;

    protected Integer index;

    protected Boolean banned;

    protected Boolean enabled;

    protected Boolean admin;

    protected String phone;

    protected String company;

    protected String homepage;

    protected String iban;

    protected String swift;

    protected List<Role> authorities;

    /**
     * Returns user id.
     *
     * @return user id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets user id.
     *
     * @param id uder id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns index.
     *
     * @return index.
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * Sets index.
     *
     * @param index index.
     */
    public void setIndex(Integer index) {
        this.index = index;
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
    public void setBanned(Boolean banned) {
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
    public void setAdmin(Boolean admin) {
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
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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
     * Sets user email.
     *
     * @param email email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * get authorities
     *
     * @return list of authorities
     */
    public List<Role> getAuthorities() {
        return authorities;
    }

    /**
     * set authorities
     *
     * @param authorities list of authorities
     */
    public void setAuthorities(List<Role> authorities) {
        this.authorities = authorities;
    }

    /**
     * @return
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
     * @return
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

    public String getCompany() {
        return company;
    }

    /**
     * @param company
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * @return
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
     * @return
     */
    public String getSecToken() {
        return secToken;
    }

    /**
     * @param secToken
     */
    public void setSecToken(String secToken) {
        this.secToken = secToken;
    }

    /**
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
