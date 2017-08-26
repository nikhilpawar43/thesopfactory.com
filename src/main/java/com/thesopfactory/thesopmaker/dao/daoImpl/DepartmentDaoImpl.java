package com.thesopfactory.thesopmaker.dao.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.thesopfactory.thesopmaker.dao.DepartmentDao;
import com.thesopfactory.thesopmaker.model.Department;

/**
Author- Nikhil
Date - Jul 16, 2017
*/
@Repository
@SuppressWarnings("unchecked")
public class DepartmentDaoImpl implements DepartmentDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Department> getAllDepartments() {
		String sql = "FROM Department";
		return entityManager.createQuery( sql )
				.getResultList();
	}
	
	@Override
	public Department getDepartmentByName( String departmentName ) {
		String sql = "FROM Department WHERE name=:departmentName";
		return (Department) entityManager.createQuery( sql )
				.setParameter("departmentName", departmentName)
				.getSingleResult();
	}
}
