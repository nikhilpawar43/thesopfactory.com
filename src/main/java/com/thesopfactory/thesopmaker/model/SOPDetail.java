package com.thesopfactory.thesopmaker.model;

import java.io.Serializable;

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
@Table( name = "sop_details" )
public class SOPDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	private long id;
	
	@ManyToOne
	private SOP sop;
	
	@ManyToOne
	private Question question;
	
	private String userInput;
	
	private long userOptionId;
	
	public long getId() {	 return id;		}

	public void setId(long id) {	this.id = id;	}

	public SOP getSop() {	return sop;		}

	public void setSop(SOP sop) {	this.sop = sop;		}

	public String getUserInput() {	  return userInput;		}

	public void setUserInput(String userInput) {	this.userInput = userInput;		}

	public long getUserOptionId() {		return userOptionId;	}

	public void setUserOptionId(long userOptionId) {	this.userOptionId = userOptionId;	}
	
	public Question getQuestion() {  	return question;	}

	public void setQuestion(Question question) {	this.question = question;	 }
	
	@Override
	public String toString() {
		return "SOPDetails [id=" + id + ", sop=" + sop + ", question=" + question + ", userInput=" + userInput
				+ ", userOptionId=" + userOptionId + "]";
	}

}
