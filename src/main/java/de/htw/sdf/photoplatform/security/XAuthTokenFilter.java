/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Sifts through all incoming requests and installs a Spring Security principal
 * if a header corresponding to a valid user is found.
 *
 * @author <a href="mailto:philip@sorst.net">Philip W. Sorst</a>
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class XAuthTokenFilter extends GenericFilterBean {

    static final String FILTER_APPLIED = "__spring_security_scpf_applied";
    private final UserDetailsService detailsService;
    private final TokenUtils tokenUtils = new TokenUtils();
    private final AuthenticationManager authenticationManager;
    protected Logger log = Logger.getLogger(XAuthTokenFilter.class);
    private String xAuthTokenHeaderName = "x-auth-token";

    /**
     * XAuthTokenFilter constructor.
     * 
     * @param userDetailsService
     *            the user details service
     * @param authenticationManager
     *            the authentication manager
     */
    public XAuthTokenFilter(UserDetailsService userDetailsService,
            AuthenticationManager authenticationManager) {
        this.detailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1,
            FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) arg0;
            String authToken = httpServletRequest
                    .getHeader(this.xAuthTokenHeaderName);

            arg0.setAttribute(FILTER_APPLIED, Boolean.TRUE);

            if (StringUtils.hasText(authToken)) {
                String username = this.tokenUtils
                        .getUserNameFromToken(authToken);

                UserDetails details = this.detailsService
                        .loadUserByUsername(username);

                if (this.tokenUtils.validateToken(authToken, details)) {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                            details, details.getPassword(),
                            details.getAuthorities());

                    token.setDetails(new WebAuthenticationDetailsSource()
                            .buildDetails(httpServletRequest));
                    Authentication authentication = this.authenticationManager
                            .authenticate(token);
                    SecurityContextHolder.getContext().setAuthentication(
                            authentication);
                    log.error("========================> "
                            + authentication.getName() + " , "
                            + authentication.isAuthenticated());

                }

            }
            filterChain.doFilter(arg0, arg1);
        } catch (Exception ex) {
            log.debug(ex.getMessage());
        }
    }

}
