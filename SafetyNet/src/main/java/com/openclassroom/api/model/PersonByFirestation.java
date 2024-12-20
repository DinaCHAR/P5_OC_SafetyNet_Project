package com.openclassroom.api.model;

import java.util.List;

public class PersonByFirestation {

	private List<Person> persons;
	private int childCount;
	private int adultCount;
	
	public PersonByFirestation() {
		
	}
	
	public List<Person> getPersons() {
		return persons;
	}
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	public int getChildCount() {
		return childCount;
	}
	public void setChildCount(int childCount) {
		this.childCount = childCount;
	}
	public int getAdultCount() {
		return adultCount;
	}
	public void setAdultCount(int adultCount) {
		this.adultCount = adultCount;
	}
	
	
	
}
