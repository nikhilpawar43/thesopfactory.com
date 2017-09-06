package com.thesopfactory.thesopmaker.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;



/**
Author- Nikhil
Date - Jun 29, 2017
*/

@Entity
@Table( name = "questions" )
public class Question implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	
	@OneToOne
	@JoinColumn( name = "department_id" )
	private Department department;

	private String page;
	
	private String question;
	
	@Transient
	private List<Option> options;
	
	@Transient
	private String userInput;
	
	@Transient
	private long userOption;
	
	public long getId() {	return id;		}

	public void setId(long id) {	this.id = id;	}
	
	public Department getDepartment() {		return department;	}

	public void setDepartment(Department department) {		this.department = department;	}

	public String getPage() {	return page;	}

	public void setPage(String page) {	this.page = page;	}

	public String getQuestion() {	return question;	}

	public void setQuestion(String question) {	this.question = question;	}

	public List<Option> getOptions() {	return options;		}

	public void setOptions(List<Option> options) {	this.options = options;		}
	
	public String getUserInput() {	  return userInput;		}

	public void setUserInput(String userInput) {	this.userInput = userInput;		}

	public long getUserOption() {	 return userOption;	 }

	public void setUserOption(long userOption) {	  this.userOption = userOption;		}

	public static long getSerialversionuid() {	return serialVersionUID;	}

	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Question))
			return false;
		Question other = (Question) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", department=" + department + ", page=" + page + ", question=" + question
				+ ", options=" + options + "]";
	}

}
