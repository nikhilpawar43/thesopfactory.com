package com.thesopfactory.thesopmaker.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
Author- Nikhil
Date - Sep 7, 2017
*/
/**
 * Always returns a 401 error code to the client.
 */

@Component( "restAuthenticationEntryPoint" )
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence( HttpServletRequest request, HttpServletResponse response, AuthenticationException exception )
			throws IOException, ServletException {
		
		SecurityUtils.sendError(response, exception, HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
	}

}
