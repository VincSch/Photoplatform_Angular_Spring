/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.htw.sdf.photoplatform.persistence.AbstractBaseAuditEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entity class for a user representing the corresponding database table.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
@Entity
@Table(name = "SYS_USER")
public class User extends AbstractBaseAuditEntity implements UserDetails {

    private static final long serialVersionUID = 3719799602561353931L;

    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "SECTOKEN")
    private String secToken;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "COMPANY")
    private String company;

    @Column(name = "HOMEPAGE")
    private String homepage;

    @Column(name = "IBAN")
    private String iban;

    @Column(name = "SWIFT")
    private String swift;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<UserRole> userRoles;

    @Column(name = "IS_ACCOUNT_NON_LOCKED")
    private boolean accountNonLocked;

    @Column(name = "IS_ENABLED")
    private boolean enabled;

    /**
     * User constructor.
     */
    public User() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<String> roles = new HashSet<>();
        if (userRoles == null) {
            return null;
        }

        for (UserRole userRole : userRoles) {
            roles.add(userRole.getRole().getName());
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return authorities;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Set username.
     *
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set username.
     *
     * @param name the user name to set
     */
    public void setUserName(String name) {
        this.username = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Set password.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set email.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns token.
     *
     * @return the sec toke
     */
    public String getSecToken() {
        return secToken;
    }

    /**
     * Set token.
     *
     * @param secToken the sec token to set
     */
    public void setSecToken(String secToken) {
        this.secToken = secToken;
    }

    /**
     * Returns user roles.
     *
     * @return the user roles
     */
    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    /**
     * Set user roles.
     *
     * @param userRoles
     */
    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    /**
     * Set locked value. If true than locked, else non locked
     *
     * @param isAccountNonLocked the account non locked to set
     */
    public void setAccountNonLocked(boolean isAccountNonLocked) {
        this.accountNonLocked = isAccountNonLocked;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param isEnabled the enable to set
     */
    public void setEnabled(boolean isEnabled) {
        this.enabled = isEnabled;
    }

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
}
