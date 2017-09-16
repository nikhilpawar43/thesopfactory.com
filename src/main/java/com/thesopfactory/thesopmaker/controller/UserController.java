package com.thesopfactory.thesopmaker.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thesopfactory.thesopmaker.model.Department;

/**
Author- Nikhil
Date - Sep 9, 2017
*/
@RestController
@RequestMapping( "users" )
public class UserController {

	@RequestMapping( value="/user", method=RequestMethod.GET)
	public Principal getUser( Principal user ) {
		
		return user;
	}
	
}
