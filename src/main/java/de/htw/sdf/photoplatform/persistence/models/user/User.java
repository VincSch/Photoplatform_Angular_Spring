/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.persistence.models.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import de.htw.sdf.photoplatform.persistence.AbstractBaseAuditEntity;

/**
 * Entity class for a user representing the corresponding database table.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 *
 */
@Entity
@Table(name = "SYS_USER")
public class User extends AbstractBaseAuditEntity implements UserDetails
{
    private static final long serialVersionUID = 3719799602561353931L;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "SECTOKEN")
    private String secToken;

    @Column(name = "EMAIL")
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<UserRole> userRoles;

    @OneToOne(mappedBy = "user")
    private UserProfile userProfile;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private UserBank userBank;

    @Column(name = "IS_ACCOUNT_NON_LOCKED")
    private boolean accountNonLocked;

    @Column(name = "IS_ENABLED")
    private boolean enabled;

    /**
     * User constructor.
     */
    public User()
    {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection< ? extends GrantedAuthority> getAuthorities()
    {
        Set<String> roles = new HashSet<>();
        if (userRoles == null)
        {
            return null;
        }

        for (UserRole userRole : userRoles)
        {
            roles.add(userRole.getRole().getName());
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        for (String role : roles)
        {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return authorities;
    }

    /**
     * Returns user profile.
     *
     * @return user profile
     */
    public UserProfile getUserProfile()
    {
        return userProfile;
    }

    /**
     * Set user profile.
     *
     * @param userProfile
     *            user profile
     */
    public void setUserProfile(UserProfile userProfile)
    {
        this.userProfile = userProfile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUsername()
    {
        return username;
    }

    /**
     * Set username.
     * 
     * @param username
     *            username
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * Set username.
     * 
     * @param name
     *            the user name to set
     */
    public void setUserName(String name)
    {
        this.username = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPassword()
    {
        return password;
    }

    /**
     * Set password.
     *
     * @param password
     *            the password to set
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * Returns email.
     *
     * @return the email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Set email.
     *
     * @param email
     *            the email to set
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Returns token.
     *
     * @return the sec toke
     */
    public String getSecToken()
    {
        return secToken;
    }

    /**
     * Set token.
     *
     * @param secToken
     *            the sec token to set
     */
    public void setSecToken(String secToken)
    {
        this.secToken = secToken;
    }

    /**
     * Returns user roles.
     *
     * @return the user roles
     */
    public List<UserRole> getUserRoles()
    {
        return userRoles;
    }

    /**
     * Set user roles.
     * 
     * @param userRoles
     */
    public void setUserRoles(List<UserRole> userRoles)
    {
        this.userRoles = userRoles;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAccountNonLocked()
    {
        return accountNonLocked;
    }

    /**
     * Set locked value. If true than locked, else non locked
     *
     * @param isAccountNonLocked
     *            the account non locked to set
     */
    public void setAccountNonLocked(boolean isAccountNonLocked)
    {
        this.accountNonLocked = isAccountNonLocked;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEnabled()
    {
        return enabled;
    }

    /**
     * @param isEnabled
     *            the enable to set
     */
    public void setEnabled(boolean isEnabled)
    {
        this.enabled = isEnabled;
    }

    /**
     * Return user bank account.
     * 
     * @return user bank account
     */
    public UserBank getUserBank()
    {
        return userBank;
    }

    /**
     * Sets user bank account.
     * 
     * @param userBank
     *            bank account
     */
    public void setUserBank(UserBank userBank)
    {
        this.userBank = userBank;
    }
}
