package com.thesopfactory.thesopmaker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
Author- Nikhil
Date - Jul 29, 2017
*/
@Entity
@Table( name = "options")
public class Option implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	
	private String value;
	
	/*@ManyToOne
	@JoinColumn( name= "question_id" )
	private Question question;*/
	
	@Column( name= "question_id" )
	private long questionId;

	public long getId() {	return id;		}

	public void setId(long id) {	this.id = id;	}

	public String getValue() {		return value;	}

	public void setValue(String value) {	this.value = value;		}
	
	public long getQuestionId() {	 return questionId;		}

	public void setQuestionId(long questionId) {	this.questionId = questionId;	 }

	@Override
	public String toString() {
		return "Option [id=" + id + ", value=" + value + ", questionId=" + questionId + "]";
	}

	/*
	public Question getQuestion() {		return question;	}

	public void setQuestion(Question question) {	this.question = question;	}

	@Override
	public String toString() {
		return "Option [id=" + id + ", value=" + value + "]";
	}*/
	
}
