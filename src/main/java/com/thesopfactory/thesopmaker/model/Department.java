package com.thesopfactory.thesopmaker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
Author- Nikhil
Date - Jul 11, 2017
*/
@Entity
@Table( name = "departments" )
public class Department implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;

	@Column( name="department_name" )
	private String name;

	public long getId() {	return id;	}

	public void setId(long id) {	this.id = id;	}

	public String getName() {	return name;	}

	public void setName(String name) {	this.name = name;	}

	@Override
	public String toString() {	
		return "Department [id=" + id + ", name=" + name + "]";
	}
	
}
