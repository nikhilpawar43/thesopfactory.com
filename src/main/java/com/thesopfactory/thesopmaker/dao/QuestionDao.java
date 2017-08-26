package com.thesopfactory.thesopmaker.dao;

import java.util.List;

import com.thesopfactory.thesopmaker.model.Question;

/**
Author- Nikhil
Date - Jul 22, 2017
*/
public interface QuestionDao {

	List<Question> getQuestionsListByPagename( String pageName );
	List<Question> getQuestionsListByPagenameAndDepartment( String pageName, long departmentId );
}
