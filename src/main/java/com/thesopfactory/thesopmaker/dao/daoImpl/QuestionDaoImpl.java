package com.thesopfactory.thesopmaker.dao.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.thesopfactory.thesopmaker.dao.QuestionDao;
import com.thesopfactory.thesopmaker.model.Question;

/**
Author- Nikhil
Date - Jul 22, 2017
*/
@Repository
@Transactional
@SuppressWarnings("unchecked")
public class QuestionDaoImpl implements QuestionDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Question> getQuestionsListByPagename( String pageName ) {
		
		String sql = "FROM Question WHERE page=:page";
		return entityManager.createQuery( sql ).
				setParameter("page", pageName).
				getResultList();
	}
	
	@Override
	public List<Question> getQuestionsListByPagenameAndDepartment( String pageName, long departmentId ) {
		String sql = "FROM Question WHERE page=:page AND department_id=:departmentId";
		
		List<Question> questions = entityManager.createQuery( sql ).
				setParameter("page", pageName).
				setParameter("departmentId", departmentId).
				getResultList();
		
		return questions; 
	}

}
