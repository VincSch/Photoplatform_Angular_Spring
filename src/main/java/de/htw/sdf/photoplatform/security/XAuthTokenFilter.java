/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.security;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;

/**
 * Sifts through all incoming requests and installs a Spring Security principal
 * if a header corresponding to a valid user is found.
 *
 * @author <a href="mailto:philip@sorst.net">Philip W. Sorst</a>
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 * @author <a href="mailto:s0531603@htw-berlin.de">Daniil Tomilow</a>
 */
public class XAuthTokenFilter extends GenericFilterBean {

    static final String FILTER_APPLIED = "__spring_security_scpf_applied";
    private final UserDetailsService userDetailsService;
    protected Logger log = Logger.getLogger(XAuthTokenFilter.class);
    private String xAuthTokenHeaderName = "x-auth-token";

    XAuthTokenFilter(final UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1,
                         FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) arg0;
            String authToken = httpServletRequest
                    .getHeader(this.xAuthTokenHeaderName);

            arg0.setAttribute(FILTER_APPLIED, Boolean.TRUE);

            //TODO This code can be used to retrieve the authenticate an image request
//            if (httpServletRequest.getRequestURI().startsWith("/api/image/")) {
//                log.info(httpServletRequest.getMethod() + " : " + httpServletRequest.getRequestURI());
//                           Cookie[] cookies = httpServletRequest.getCookies();
//                           Cookie cookie = cookies[0];
//                          String domain = cookie.getDomain();
//                         String name = cookie.getName();
//                           String value = URLDecoder.decode(cookie.getValue(), "UTF-8");
//                           HashMap obj = new ObjectMapper().readValue(value, HashMap.class);
//            }
            
            String cookieUsername = TokenUtils.getUserNameFromToken(authToken);
            if (StringUtils.hasText(authToken)) {
                String username = TokenUtils.getUserNameFromToken(authToken);
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        username, authToken);

                token.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(httpServletRequest));
                Authentication authentication = authenticate(token);

                SecurityContextHolder.getContext().setAuthentication(
                        authentication);
                log.debug("request from: "
                        + authentication.getName() + " , authenticated:"
                        + authentication.isAuthenticated());

            }
            filterChain.doFilter(arg0, arg1);
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
    }

    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        String username = (String) token.getPrincipal();
        String authToken = (String) token.getCredentials(); // secToken

        UserDetails user = this.userDetailsService.loadUserByUsername(username);

        if (!TokenUtils.validateToken(authToken, user)) {
            throw new BadCredentialsException(
                    "Invalid username password or token Expired.");
        }

        return new UsernamePasswordAuthenticationToken(user, null,
                user.getAuthorities());
    }
}
