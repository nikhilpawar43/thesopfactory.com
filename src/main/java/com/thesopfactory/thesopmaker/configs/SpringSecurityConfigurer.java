package com.thesopfactory.thesopmaker.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.thesopfactory.thesopmaker.security.RestAccessDeniedHandler;
import com.thesopfactory.thesopmaker.security.RestAuthenticationEntryPoint;
import com.thesopfactory.thesopmaker.security.RestAuthenticationFailureHandler;
import com.thesopfactory.thesopmaker.security.RestAuthenticationSuccessHandler;

/**
Author- Nikhil
Date - Sep 7, 2017
*/
@Configuration
@EnableWebSecurity
public class SpringSecurityConfigurer extends WebSecurityConfigurerAdapter {

	private static final String PREMIUM_USER = "USER";
	private static final String ADMIN = "ADMIN";
	
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	
	@Autowired
	private RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;
	
	@Autowired
	private RestAccessDeniedHandler restAccessDeniedHandler;
	
	@Autowired
	private RestAuthenticationFailureHandler restAuthenticationFailureHandler;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService( userDetailsService );
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers( "/images/**", "/css/**", "/js/**", "/index.html", "/partials/**", "/", "/error/**", "/home/login.html" );
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
		   .headers().disable()
		   .csrf().disable()
		   .authorizeRequests()
			.antMatchers("/sopwizard/**").access( "isFullyAuthenticated() and hasRole('ROLE_ADMIN')" )
		    .and()
		   .exceptionHandling()
		    .accessDeniedPage("/#/home/403.html")
		    .authenticationEntryPoint( restAuthenticationEntryPoint )
		    .accessDeniedHandler( restAccessDeniedHandler )
		    .and()
		   .formLogin()
		   	.loginPage( "/#/home/login.html" )
		    .loginProcessingUrl("/loginValidationUrl")
		    .defaultSuccessUrl("/home/sopmaker-create.html")
		    .successHandler( restAuthenticationSuccessHandler )
		    .failureHandler( restAuthenticationFailureHandler )
		    .usernameParameter("username")
		    .passwordParameter("password")
		    .permitAll();
		
	}
	
}

