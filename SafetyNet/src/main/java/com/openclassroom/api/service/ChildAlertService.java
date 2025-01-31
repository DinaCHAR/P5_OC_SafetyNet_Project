package com.openclassroom.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassroom.api.model.ChildAlert;
import com.openclassroom.api.model.MedicalRecord;
import com.openclassroom.api.model.Person;
import com.openclassroom.api.repository.MyRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChildAlertService {

	 @Autowired
	    private MyRepository myRepository;

	    /**
	     * Cette méthode retourne la liste des enfants et des membres du foyer pour une adresse donnée.
	     * @param address L'adresse pour laquelle on recherche les personnes.
	     * @return Un objet ChildAlert contenant les enfants et membres du foyer.
	     */
	    public ChildAlert getChildrenByAddress(String address) {
	        List<Map<String, Object>> children = new ArrayList<>();
	        List<Map<String, Object>> householdMembers = new ArrayList<>();

	        // Trouver toutes les personnes vivant à l'adresse donnée
	        for (Person person : myRepository.getPersons()) {
	            if (person.getAddress().equalsIgnoreCase(address)) {
	            	List<MedicalRecord> medicalRecords = (List<MedicalRecord>) findMedicalRecordForPerson(person);
	            	MedicalRecord medicalRecord = null;
	            	
	            	//CORRECTION !!!
	            	if (!medicalRecords.isEmpty()) {
	            		medicalRecord = medicalRecords.get(0);
	            	} else {
	            		continue;
	            	}

	                if (medicalRecord != null) {
	                    int age = medicalRecord.getAge();
	                    
	                    if (age <= 18) {  // Verification que l'enfant est moins de 18ans
	                        Map<String, Object> childInfo = new HashMap<>();
	                        childInfo.put("firstName", person.getFirstName());
	                        childInfo.put("lastName", person.getLastName());
	                        childInfo.put("age", age);
	                        children.add(childInfo); // Ajouter à la liste des enfants
	                    } else {  // Membres adultes du foyer
	                        Map<String, Object> memberInfo = new HashMap<>();
	                        memberInfo.put("firstName", person.getFirstName());
	                        memberInfo.put("lastName", person.getLastName());
	                        householdMembers.add(memberInfo);  // Ajouter à la liste des membres du foyer
	                    }
	                }
	            }
	        }

	        // Créer un objet ChildAlert avec les informations collectées
	        ChildAlert childAlert = new ChildAlert();
	        childAlert.setChildren(children.isEmpty() ? null : children);  // Si aucun enfant, on retourne null
	        childAlert.setHouseholdMembers(householdMembers.isEmpty() ? null : householdMembers);  // Idem pour les membres du foyer

	        return childAlert;
	    }

	    /**
	     * Recherche du MedicalRecord pour une personne donnée.
	     */
	    private List<MedicalRecord> findMedicalRecordForPerson(Person person) {
	        // Accède à la liste de tous les dossiers médicaux
	        return myRepository.getMedicalRecords().stream()
	        
	                // Filtre les dossiers médicaux pour trouver ceux qui correspondent exactement au prénom et au nom de la personne
	                .filter(record -> 
	                    record.getFirstName().equalsIgnoreCase(person.getFirstName()) && // Comparaison du prénom
	                    record.getLastName().equalsIgnoreCase(person.getLastName()))    // Comparaison du nom de famille
	                
	                // Collecte tous les dossiers correspondants dans une liste
	                .collect(Collectors.toList());
	    }
	   
	}