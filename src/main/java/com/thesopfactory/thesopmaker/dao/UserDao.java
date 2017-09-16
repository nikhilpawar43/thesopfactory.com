package com.thesopfactory.thesopmaker.dao;

import com.thesopfactory.thesopmaker.model.User;

/**
Author- Nikhil
Date - Sep 7, 2017
*/
public interface UserDao {

	User getUserByUsername( String username );
}
