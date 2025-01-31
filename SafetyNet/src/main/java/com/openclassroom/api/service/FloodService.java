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
	     * Cette méthode retourne une liste des foyers (regroupés par adresse) desservis par les casernes spécifiées.
	     *
	     * @param stationNumbers Liste des numéros des casernes de pompiers.
	     * @return Un mapping où chaque clé est une adresse et chaque valeur est une liste des informations des habitants.
	     */
	    public Map<String, List<Map<String, Object>>> getHouseholdsByStations(List<Integer> stationNumbers) {
	        // Étape 1 : Identifier les adresses desservies par les casernes données.
	    	// Récupère les adresses des casernes correspondant aux numéros de stations fournis
	    	Set<String> addresses = myRepository.getFireStations().stream()
	    	    // Filtre les casernes dont le numéro de station est dans la liste donnée
	    	    .filter(fs -> stationNumbers.contains(Integer.valueOf(fs.getStation()))) 
	    	    // Extrait uniquement les adresses des casernes filtrées
	    	    .map(fs -> fs.getAddress())
	    	    // Collecte les adresses dans un ensemble (Set) pour éviter les doublons
	    	    .collect(Collectors.toSet());

	    	// Initialise une structure de données pour stocker les foyers groupés par adresse
	    	Map<String, List<Map<String, Object>>> households = new HashMap<>();

	        // Étape 2 : Itérer sur les adresses récupérées pour collecter les informations des habitants.
	        for (String address : addresses) {
	            // Trouver toutes les personnes vivant à cette adresse.
	            List<Person> personsAtAddress = myRepository.getPersons().stream()
	                    .filter(person -> person.getAddress().equalsIgnoreCase(address)) // Vérification adresse insensible à la casse.
	                    .collect(Collectors.toList());

	            // Liste pour stocker les informations des habitants (nom, age, telephone, medical...).
	            List<Map<String, Object>> residentsInfo = new ArrayList<>();

	            // Étape 3 : Collecter les informations pour chaque habitant à cette adresse.
	            for (Person person : personsAtAddress) {
	                Map<String, Object> residentData = new HashMap<>();
	                residentData.put("firstName", person.getFirstName()); // Prenom de l'habitant.
	                residentData.put("lastName", person.getLastName()); // Nom de l'habitant.
	                residentData.put("phone", person.getPhone()); // Telephone de l'habitant.

	                // Récupérer le dossier médical de la personne si disponible.
	                MedicalRecord medicalRecord = findMedicalRecordForPerson(person);
	                if (medicalRecord != null) {
	                    residentData.put("age", medicalRecord.getAge()); // Calcul et ajout de l'âge.
	                    residentData.put("medications", medicalRecord.getMedications()); // Liste des médicaments.
	                    residentData.put("allergies", medicalRecord.getAllergies()); // Liste des allergies.
	                }

	                // Ajouter les informations collectées pour cette personne.
	                residentsInfo.add(residentData);
	            }

	            // Associer les informations des habitants à leur adresse dans la map finale.
	            households.put(address, residentsInfo);
	        }

	        // Retourner le mapping final des foyers par adresse.
	        return households;
	    }

	    /**
	     * Cette méthode cherche un dossier médical pour une personne spécifique
	     * en se basant sur son prénom et son nom.
	     *
	     * @param person L'objet Person dont on veut retrouver le dossier médical.
	     * @return Le dossier médical correspondant ou null si aucun dossier n'est trouvé.
	     */
	    private MedicalRecord findMedicalRecordForPerson(Person person) {
	        // Rechercher dans la liste des dossiers médicaux un enregistrement correspondant au prénom et au nom.
	        return myRepository.getMedicalRecords().stream()
	                .filter(record ->
	                    record.getFirstName().equalsIgnoreCase(person.getFirstName()) &&
	                    record.getLastName().equalsIgnoreCase(person.getLastName())) // Comparaison insensible à la casse.
	                .findFirst() // Récupère le premier dossier correspondant.
	                .orElse(null); // Retourne null si aucun dossier n'est trouvé.
	    }
	}