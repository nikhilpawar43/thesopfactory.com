package com.thesopfactory.thesopmaker.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
Author- Nikhil
Date - Jul 29, 2017
*/

@Entity
@Table( name = "sops" )
public class SOP implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	private long id;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Department department;
	
	@Column( name = "creation_date" )
	private Date creationDate;

	public long getId() {	return id;	  }

	public void setId(long id) {	this.id = id;	 }

	public User getUser() {		return user;	}

	public void setUser(User user) {	this.user = user;	 }

	public Department getDepartment() {		return department;	  }

	public void setDepartment(Department department) {	  this.department = department;		}
	
	public Date getCreationDate() {		return creationDate;	 }

	public void setCreationDate(Date creationDate) {	 this.creationDate = creationDate;	 }

	@Override
	public String toString() {
		return "SOP [id=" + id + ", user=" + user + ", department=" + department + ", creationDate=" + creationDate
				+ "]";
	}
	
}