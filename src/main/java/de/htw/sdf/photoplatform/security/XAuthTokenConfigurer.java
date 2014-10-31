/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 
 * @author <a href="mailto:philip@sorst.net">Philip W. Sorst</a>
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class XAuthTokenConfigurer extends
        SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>
{

    private UserDetailsService detailsService;
    private AuthenticationManager authenticationManager;

    /**
     * XAuthTokenConfigurer constructor.
     * 
     * @param detailsService
     *            the details service
     * @param authenticationManager
     *            the authentication manager
     */
    public XAuthTokenConfigurer(
            UserDetailsService detailsService,
            AuthenticationManager authenticationManager)
    {
        this.detailsService = detailsService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(HttpSecurity http) throws Exception
    {
        XAuthTokenFilter customFilter = new XAuthTokenFilter(detailsService, authenticationManager);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
