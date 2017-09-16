package com.thesopfactory.thesopmaker.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
Author- Nikhil
Date - Sep 11, 2017
*/
/**
 * Returns a 401 error code (Unauthorized) to the client, when Ajax authentication fails.
 */
@Component( "restAuthenticationFailureHandler" )
public class RestAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure( HttpServletRequest request, HttpServletResponse response, AuthenticationException exception ) throws IOException, ServletException {

		SecurityUtils.sendError(response, exception, HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
	}
	
}
