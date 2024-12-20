package com.openclassroom.api.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MedicalRecord {
	
	@JsonProperty("firstName")
	private String firstName;
	@JsonProperty("lastName")
	private String lastName;
	@JsonProperty("birthdate")
	private String birthdate;
	@JsonProperty("medications")
    private List<String> medications;  // Liste de chaînes de caractères
    @JsonProperty("allergies")
    private List<String> allergies; // Liste de chaînes de caractères
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}	
	public List<String> getMedications() {
		return medications;
	}
	public void setMedications(List<String> medications) {
		this.medications = medications;
	}
	public List<String> getAllergies() {
		return allergies;
	}
	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}
	public MedicalRecord() {
		
	}
	
	@Override
	public String toString() {
		return "MedicalRecord [firstName=" + firstName + ", lastName=" + lastName + ", birthdate=" + birthdate
				+ ", medications=" + medications + ", allergies=" + allergies + "]";
	}
	
	  // Ajout de la méthode pour calculer l'âge
    public int getAge() {
        // Formateur pour la date au format "dd/MM/yyyy"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        // Conversion de la date de naissance en LocalDate
        LocalDate birthDate = LocalDate.parse(this.birthdate, formatter);
        // Date actuelle
        LocalDate currentDate = LocalDate.now();
        // Calcul de l'écart en années entre la date de naissance et la date actuelle
        return Period.between(birthDate, currentDate).getYears();
    }
}
