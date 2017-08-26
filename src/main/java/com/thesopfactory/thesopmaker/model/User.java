package com.thesopfactory.thesopmaker.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
Author- Nikhil
Date - Jul 29, 2017
*/
@Entity
@Table( name = "users" )
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	
	private String firstname;
	
	private String lastname;
	
	private String emailId;
	
	private boolean active;

	public long getId() {	return id;		}

	public void setId(long id) {	this.id = id;	}

	public String getFirstname() {	 return firstname;	  }

	public void setFirstname(String firstname) {	this.firstname = firstname;		}

	public String getLastname() {	 return lastname;  	 }

	public void setLastname(String lastname) {	  this.lastname = lastname;		}

	public String getEmailId() {	return emailId;		}

	public void setEmailId(String emailId) {	this.emailId = emailId;		}

	public boolean isActive() {		return active;		}

	public void setActive(boolean active) {		this.active = active;	 }

	@Override
	public String toString() {
		return "User [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", emailId=" + emailId
				+ ", active=" + active + "]";
	}
	
}
