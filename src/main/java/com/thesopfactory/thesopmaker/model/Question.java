package com.thesopfactory.thesopmaker.model;

import java.util.Set;

/**
Author- Nikhil
Date - Jun 29, 2017
*/
public class Question {

	private long id;
	private String value;
	private String page;
	private Set<String> options;
	private String userInput;
	private int answer;
	
	public void setId(long id) {	this.id = id;	}
	
	public long getId() {	return id;	}
	
	public String getValue() {	return value;	}
	
	public void setValue(String value) {	this.value = value;		}
	
	public String getPage() {	return page;	}
	
	public void setPage(String page) {	this.page = page;	}
	
	public Set<String> getOptions() {	return options;		}
	
	public void setOptions(Set<String> options) {	this.options = options;		}
	
	public void setUserInput(String userInput) {	this.userInput = userInput;		}
	
	public String getUserInput() {	return userInput;	}
	
	public void setAnswer(int answer) {		this.answer = answer;	}
	
	public int getAnswer() {	return answer;	}
}
