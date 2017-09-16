package com.thesopfactory.thesopmaker.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.thesopfactory.thesopmaker.model.User;

/**
Author- Nikhil
Date - Sep 11, 2017
*/
@Component( "authenticationSuccessHandler" )
public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private Logger log = LoggerFactory.getLogger( RestAuthenticationSuccessHandler.class );
	
	@Override
	public void onAuthenticationSuccess( HttpServletRequest request, HttpServletResponse response, Authentication authentication )
			throws IOException, ServletException {
		
		User user = (User) authentication.getPrincipal();
		log.info( "The user: {} has been authenticated successfully.", user.getUsername() );
		
		SecurityUtils.sendResponse( response, HttpServletResponse.SC_OK, user );
	}

}
