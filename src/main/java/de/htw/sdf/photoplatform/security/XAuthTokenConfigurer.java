package de.htw.sdf.photoplatform.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * @author Philip W. Sorst (philip@sorst.net)
 * @author Josh Long (josh@joshlong.com)
 */
public class XAuthTokenConfigurer extends
		SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private UserDetailsService detailsService;
	private AuthenticationManager authenticationManager;

	public XAuthTokenConfigurer(UserDetailsService detailsService,
			AuthenticationManager authenticationManager) {
		this.detailsService = detailsService;
		this.authenticationManager = authenticationManager;
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		XAuthTokenFilter customFilter = new XAuthTokenFilter(detailsService,
				authenticationManager);
		http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
