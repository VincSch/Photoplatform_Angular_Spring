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
@Table(name = "RB_USER")
public class User extends AbstractBaseAuditEntity implements UserDetails
{

    private static final long serialVersionUID = 3719799602561353931L;

    @Column(name = "NAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "BIRTHDAY")
    private String birthday;

    @Column(name = "ABOUT")
    private String about;

    @Column(name = "CITY")
    private String city;

    @Column(name = "SECTOKEN")
    private String secToken;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<UserRole> userRoles;

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
     * {@inheritDoc}
     */
    @Override
    public String getUsername()
    {
        return username;
    }

    /**
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
     * @param password
     *            the password to set
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * @return the email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * @return the birthday
     */
    public String getBirthday()
    {
        return birthday;
    }

    /**
     * @param birthday
     *            the birthday to set
     */
    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    /**
     * @return the about
     */
    public String getAbout()
    {
        return about;
    }

    /**
     * @param about
     *            the about to set
     */
    public void setAbout(String about)
    {
        this.about = about;
    }

    /**
     * @return the city
     */
    public String getCity()
    {
        return city;
    }

    /**
     * @param city
     *            the city to set
     */
    public void setCity(String city)
    {
        this.city = city;
    }

    /**
     * @return the sec toke
     */
    public String getSecToken()
    {
        return secToken;
    }

    /**
     * @param secToken
     *            the sec token to set
     */
    public void setSecToken(String secToken)
    {
        this.secToken = secToken;
    }

    /**
     * @return the user roles
     */
    public List<UserRole> getUserRoles()
    {
        return userRoles;
    }

    /**
     * @param userRoles
     *            the user roles to set
     */
    public void setUserRole(List<UserRole> userRoles)
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

}
