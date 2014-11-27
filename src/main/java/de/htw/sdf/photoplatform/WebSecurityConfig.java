/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform;

import de.htw.sdf.photoplatform.persistence.model.Role;
import de.htw.sdf.photoplatform.repository.UserDAO;
import de.htw.sdf.photoplatform.security.XAuthTokenConfigurer;
import de.htw.sdf.photoplatform.webservice.Endpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;

/**
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
@EnableWebMvcSecurity
@EnableWebSecurity(debug = true)
@ComponentScan
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ConfigurableApplicationContext context;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(
                SessionCreationPolicy.STATELESS);

        for (String endpoint : Endpoints.securedUserEndpoints()) {
            http.authorizeRequests().antMatchers(endpoint).hasAnyRole(
                    removeRolePrefix(Role.PHOTOGRAPHER),
                    removeRolePrefix(Role.CUSTOMER),
                    removeRolePrefix(Role.ADMIN));
        }

        for (String endpoint : Endpoints.securedPhotographEndpoints()) {
            http.authorizeRequests().antMatchers(endpoint).hasAnyRole(
                    removeRolePrefix(Role.PHOTOGRAPHER),
                    removeRolePrefix(Role.ADMIN));
        }

        for (String endpoint : Endpoints.securedAdminEndpoints()) {
            http.authorizeRequests().antMatchers(endpoint)
                    .hasRole(removeRolePrefix(Role.ADMIN));
        }

        SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> securityConfigurerAdapter = new XAuthTokenConfigurer(
                userDetailsService);
        http.apply(securityConfigurerAdapter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure(AuthenticationManagerBuilder authManagerBuilder)
            throws Exception {
        UserDAO userDAO = context.getBean(UserDAO.class);
        authManagerBuilder.userDetailsService(userDAO).passwordEncoder(
                passwordEncoder());
    }

    /**
     * {@inheritDoc}
     */
    @Bean(name = "myAuthManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Remove prefix ROLE_ cause Spring-Security requires format without ROLE_
     * for Web Security set up.
     *
     * @param roleName role name like ROLE_ADMIN
     * @return role name like ADMIN (deleted Prefix ROLE_)
     */
    private String removeRolePrefix(String roleName) {
        return roleName.substring(5);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
