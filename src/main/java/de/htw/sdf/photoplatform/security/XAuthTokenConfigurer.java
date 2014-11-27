/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author <a href="mailto:philip@sorst.net">Philip W. Sorst</a>
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class XAuthTokenConfigurer extends
        SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final UserDetailsService userDetailsService;

    /**
     * XAuthTokenConfigurer constructor.
     *
     * @param userDetailsService the userDetailsService
     */
    public XAuthTokenConfigurer(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new XAuthTokenFilter(userDetailsService),
                UsernamePasswordAuthenticationFilter.class);
    }

}
