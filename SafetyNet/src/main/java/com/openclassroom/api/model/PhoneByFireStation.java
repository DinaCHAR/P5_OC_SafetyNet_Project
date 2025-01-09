package com.openclassroom.api.model;

import java.util.List;

public class PhoneByFireStation {
	
	private List<String> phoneNumbers;
	
	public PhoneByFireStation() {
	}

	public List<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}
}