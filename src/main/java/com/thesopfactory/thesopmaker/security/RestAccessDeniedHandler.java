package com.thesopfactory.thesopmaker.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.thesopfactory.thesopmaker.model.User;

/**
Author- Nikhil
Date - Sep 11, 2017
*/
@Component( "restAccessDeniedHandler" )
public class RestAccessDeniedHandler implements AccessDeniedHandler {

	private Logger log = LoggerFactory.getLogger( RestAccessDeniedHandler.class );
	
	@Override
	public void handle( HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception )
			throws IOException, ServletException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if ( auth != null ) {
			User user = ( User ) auth.getPrincipal();
			log.warn( "The user: {} attempted to access a protected web service endpoint: {}", user.getUsername(), request.getRequestURI() );
		}
		
		SecurityUtils.sendError(response, exception, HttpServletResponse.SC_FORBIDDEN, "Not authorized resources");
	}

}
