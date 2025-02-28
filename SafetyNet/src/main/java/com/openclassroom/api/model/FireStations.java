package com.openclassroom.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FireStations {

	@JsonProperty("address")
	private String address;
	@JsonProperty("station")
	private String station;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	
	public FireStations () {
		
	}
	public FireStations(String address, String station) {
		this.address = address;
		this.station = station;
	}
	@Override
	public String toString() {
		return "FireStations [address=" + address + ", station=" + station + "]";
	}
}

