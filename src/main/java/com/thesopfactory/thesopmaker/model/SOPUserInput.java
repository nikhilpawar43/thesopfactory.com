package com.thesopfactory.thesopmaker.model;

/**
Author- Nikhil
*/
public class SOPUserInput {

	private String name;
	private int age;
	private String degree;
	private String company;
	private String hobbies;
	
	public SOPUserInput() {
		
	}

	public SOPUserInput(String name, int age, String degree, String company, String hobbies) {
		super();
		this.name = name;
		this.age = age;
		this.degree = degree;
		this.company = company;
		this.hobbies = hobbies;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}

	public String getHobbies() {
		return hobbies;
	}
	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}

	@Override
	public String toString() {
		return "SOPUserInput [name=" + name + ", age=" + age + ", degree=" + degree + ", company=" + company
				+ ", hobbies=" + hobbies + "]";
	}
	
}
