package com.thesopfactory.thesopmaker.dao.daoImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.thesopfactory.thesopmaker.dao.UserDao;
import com.thesopfactory.thesopmaker.model.User;

/**
Author- Nikhil
Date - Sep 7, 2017
*/
@Repository( "userDao" )
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public User getUserByUsername(String username) {
		
		String sql = "FROM User WHERE emailId=:username";
		User user = (User) entityManager.createQuery( sql ).setParameter( "username", username ).getSingleResult(); 
		return user;
	}

}
