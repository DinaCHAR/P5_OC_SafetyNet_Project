package com.openclassroom.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassroom.api.model.FireStations;
import com.openclassroom.api.model.Person;
import com.openclassroom.api.repository.MyRepository;

@Service
public class PhoneAlertService {

	    @Autowired
	    private MyRepository myRepository;

	    // Méthode pour obtenir les numéros de téléphone par numéro de caserne
	    public List<String> getPhoneNumbersByFirestation(String firestationNumber) {
	        // Initialiser les données
	        List<Person> persons = myRepository.getPersons();
	        List<FireStations> fireStations = myRepository.getFireStations();

	        // Trouver toutes les adresses desservies par la caserne donnée
	        List<String> addresses = fireStations.stream()
	            .filter(fs -> fs.getStation().equals(firestationNumber))
	            .map(FireStations::getAddress)
	            .collect(Collectors.toList());

	        // Récupérer les numéros de téléphone des personnes desservies par cette caserne
	        return persons.stream()
	            .filter(p -> addresses.contains(p.getAddress()))
	            .map(Person::getPhone)
	            .collect(Collectors.toList());
	    }
	}