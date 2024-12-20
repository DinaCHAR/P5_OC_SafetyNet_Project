package com.openclassroom.api.model;

import java.util.List;
import java.util.Map;

public class ChildAlert {
	
	private List<Person> persons;
	private List<Map<String, Object>> children;
	private List<Map<String, Object>> householdMembers;
	
	public ChildAlert() {
			
		}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public List<Map<String, Object>> getChildren() {
		return children;
	}

	public void setChildren(List<Map<String, Object>> children) {
		this.children = children;
	}

	public List<Map<String, Object>> getHouseholdMembers() {
		return householdMembers;
	}

	public void setHouseholdMembers(List<Map<String, Object>> householdMembers) {
		this.householdMembers = householdMembers;
	}

	
	
}