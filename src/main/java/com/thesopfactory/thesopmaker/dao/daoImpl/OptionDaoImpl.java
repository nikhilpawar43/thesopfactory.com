package com.thesopfactory.thesopmaker.dao.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.thesopfactory.thesopmaker.dao.OptionDao;
import com.thesopfactory.thesopmaker.model.Option;

/**
Author- Nikhil
Date - Jul 30, 2017
*/
@Repository
@SuppressWarnings("unchecked")
public class OptionDaoImpl implements OptionDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Option> getOptionsByQuestion(long questionId) {
		
		String sql = "FROM Option where question_id=:question_id";
		return entityManager.createQuery( sql ).setParameter("question_id", questionId).getResultList();
	}

}
