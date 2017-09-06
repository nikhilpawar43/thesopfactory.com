package com.thesopfactory.thesopmaker.dao;

import java.util.List;

import com.thesopfactory.thesopmaker.model.Department;

/**
Author- Nikhil
Date - Jul 16, 2017
*/
public interface DepartmentDao {

	List<Department> getAllDepartments();
	Department getDepartmentByName( String departmentName );
	Department getDepartmentById( long departmentId );
}
