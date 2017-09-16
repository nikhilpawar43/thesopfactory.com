package com.thesopfactory.thesopmaker.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thesopfactory.thesopmaker.dao.UserDao;
import com.thesopfactory.thesopmaker.model.User;

/**
Author- Nikhil
Date - Sep 7, 2017
*/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDao userDao;
	
	private User user;
	
	private Logger log = LoggerFactory.getLogger( UserDetailsServiceImpl.class );
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		user = userDao.getUserByUsername( username );
		
		if ( user != null ) {
			log.info( "Spring security capture the user: " + username );
			return user;
		}
		
		log.error( "Spring security is unable to find the user with username: {}", username );
		throw new UsernameNotFoundException( "User not found with username: " + username );
		
	}
	
}
