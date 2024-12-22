package com.openclassroom.api.model;

import java.util.List;

public class PersonByFirestation {

	private List<Person> persons;
	private int childCount;
	private int adultCount;
	private int personsAtAddress;
	
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
	
	public int getPersonsAtAddress() {
		return personsAtAddress;
	}

	public void setPersonsAtAddress(int personsAtAddress) {
		this.personsAtAddress = personsAtAddress;
	}
	
	
}
