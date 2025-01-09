package com.openclassroom.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassroom.api.model.MedicalRecord;
import com.openclassroom.api.model.Person;
import com.openclassroom.api.repository.MyRepository;

@Service
public class FloodService {
	
	 @Autowired
	    private MyRepository myRepository;

	    /**
	     * Cette méthode retourne une liste de tous les foyers desservis par les casernes spécifiées.
	     * 
	     * @param stationNumbers Liste des numéros de caserne.
	     * @return Un mapping des adresses et des informations des habitants associés.
	     */
	    public Map<String, List<Map<String, Object>>> getHouseholdsByStations(List<Integer> stationNumbers) {
	        // Trouver les adresses associées aux casernes données
	        @SuppressWarnings("unlikely-arg-type")
			Set<String> addresses = myRepository.getFireStations().stream()
	                .filter(fs -> stationNumbers.contains(fs.getStation())) // Vérifier si la caserne correspond
	                .map(fs -> fs.getAddress()) // Extraire les adresses associées
	                .collect(Collectors.toSet()); // Supprimer les doublons

	        // Préparer un map pour regrouper les résultats par adresse
	        Map<String, List<Map<String, Object>>> households = new HashMap<>();


	        // Itérer sur les adresses trouvées
	        for (String address : addresses) {
	            // Filtrer les personnes vivant à cette adresse
	            List<Person> personsAtAddress = myRepository.getPersons().stream()
	                    .filter(person -> person.getAddress().equalsIgnoreCase(address))
	                    .collect(Collectors.toList());

	            // Récupérer les informations pour chaque personne
	            List<Map<String, Object>> residentsInfo = new ArrayList<>();
	            for (Person person : personsAtAddress) {
	                Map<String, Object> residentData = new HashMap<>();
	                residentData.put("firstName", person.getFirstName());
	                residentData.put("lastName", person.getLastName());
	                residentData.put("phone", person.getPhone());

	                MedicalRecord medicalRecord = findMedicalRecordForPerson(person);
	                if (medicalRecord != null) {
	                    residentData.put("age", medicalRecord.getAge());
	                    residentData.put("medications", medicalRecord.getMedications());
	                    residentData.put("allergies", medicalRecord.getAllergies());
	                }

	                residentsInfo.add(residentData);
	            }

	            // Associer les informations collectées à l'adresse
	            households.put(address, residentsInfo);
	        }

	        return households;
	    }

	    /**
	     * Recherche le dossier médical (MedicalRecord) d'une personne à partir de son prénom et de son nom.
	     * 
	     * @param person L'objet Person dont on veut retrouver le dossier médical.
	     * @return Le dossier médical correspondant, ou null si aucun n'est trouvé.
	     */
	    private MedicalRecord findMedicalRecordForPerson(Person person) {
	        return myRepository.getMedicalRecords().stream()
	                .filter(record -> 
	                    record.getFirstName().equalsIgnoreCase(person.getFirstName()) &&
	                    record.getLastName().equalsIgnoreCase(person.getLastName()))
	                .findFirst()
	                .orElse(null);
	    }
	}