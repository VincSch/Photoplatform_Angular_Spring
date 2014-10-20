package de.htw.sdf.photoplatform;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;

import de.htw.sdf.photoplatform.repository.UserDAO;
import de.htw.sdf.photoplatform.security.XAuthTokenConfigurer;
import de.htw.sdf.photoplatform.webservice.common.Endpoints;
/**
 * This class represents the application config, which is done in java instead of XML
 * @author Vincent Schwarzer
 *
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@ImportResource("classpath:applicationContext.xml")
public class Application {

	static Logger log = Logger.getLogger(Application.class.getName());
	private static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		context = SpringApplication.run(Application.class);
		DBUtil dbUtil = context.getBean(DBUtil.class);
		dbUtil.insertDestData();

	}

	public static ConfigurableApplicationContext getContext() {
		return context;
	}
}

@EnableWebMvcSecurity
@EnableWebSecurity(debug = true)
@ComponentScan
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private ConfigurableApplicationContext context;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(
				SessionCreationPolicy.STATELESS);

		for (String endpoint : Endpoints.securedUserEndpoints()) {
			http.authorizeRequests().antMatchers(endpoint)
					.hasAnyRole("USER","ADMIN");
		}
		
		for (String endpoint : Endpoints.securedAdminEndpoints()) {
			http.authorizeRequests().antMatchers(endpoint)
					.hasRole("ADMIN");
		}

		SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> securityConfigurerAdapter = new XAuthTokenConfigurer(
				userDetailsServiceBean(), authenticationManagerBean());
		http.apply(securityConfigurerAdapter);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder authManagerBuilder)
			throws Exception {
		UserDAO userDAO = context.getBean(UserDAO.class);
		authManagerBuilder.userDetailsService(userDAO);
	}

	@Bean(name = "myAuthManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
