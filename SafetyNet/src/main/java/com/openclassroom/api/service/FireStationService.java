package com.openclassroom.api.service;

import com.openclassroom.api.model.FireStations;
import com.openclassroom.api.model.MedicalRecord;
import com.openclassroom.api.model.Person;
import com.openclassroom.api.model.PersonByFirestation;
import com.openclassroom.api.repository.MyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FireStationService {

	    @Autowired
	    private MyRepository myRepository;

	    /**
	     * Fonction pour obtenir les personnes couvertes par une station de pompiers donnée.
	     *
	     * @param stationNumber Le numéro de la station de pompiers
	     * @return Une Map contenant une liste de personnes couvertes, ainsi que le décompte des adultes et des enfants.
	     */
	    public PersonByFirestation getPersonsCoveredByStation(String stationNumber) {
	        // Récupération de la liste des stations de pompiers à partir du dépôt
	        List<FireStations> stations = myRepository.getFireStations();
	        // Liste pour stocker les personnes couvertes par la station demandée
	        List<Person> personsCovered = new ArrayList<>();
	        // Compteurs pour le nombre d'adultes et d'enfants
	        int adultCount = 0;
	        int childCount = 0;

	        // Parcourir toutes les stations de pompiers
	        for (FireStations station : stations) {
	            // Vérifier si le numéro de la station correspond à celui demandé
	            if (station.getStation().equals(stationNumber)) {
	                // Récupérer l'adresse associée à cette station
	                String stationAddress = station.getAddress();

	                // Rechercher toutes les personnes dont l'adresse correspond à celle de la station
	                for (Person person : myRepository.getPersons()) {
	                    // Comparer les adresses
	                    if (person.getAddress().equals(stationAddress)) {
	                        // Récupérer l'enregistrement médical associé à cette personne pour déterminer son âge
	                        MedicalRecord medicalRecord = findMedicalRecordForPerson(person);
	                        if (medicalRecord != null) {
	                            // Utilisation de la méthode `getAge()` pour calculer l'âge de la personne
	                            int age = medicalRecord.getAge();
	                            // Vérifier si la personne est un enfant (<= 18 ans) ou un adulte (> 18 ans)
	                            if (age <= 18) {
	                                childCount++; // Incrémenter le compteur des enfants
	                            } else {
	                                adultCount++; // Incrémenter le compteur des adultes
	                            }
	                        }
	                        // Ajouter la personne à la liste des personnes couvertes
	                        personsCovered.add(person);
	                    }
	                }
	            }
	        }
	        
	        PersonByFirestation personByFirestation = new PersonByFirestation();
	        personByFirestation.setPersons(personsCovered);
	        personByFirestation.setAdultCount(adultCount);
	        personByFirestation.setChildCount(childCount);

	        // Retourner la réponse
	        return personByFirestation;
	    }

	    /**
	     * Trouver l'enregistrement médical associé à une personne donnée.
	     *
	     * @param person La personne pour laquelle l'enregistrement médical est recherché
	     * @return L'enregistrement médical correspondant, ou null s'il n'est pas trouvé
	     */
	    private MedicalRecord findMedicalRecordForPerson(Person person) {
	        // Parcourir tous les enregistrements médicaux disponibles
	        for (MedicalRecord record : myRepository.getMedicalRecords()) {
	            // Vérifier si le prénom et le nom correspondent
	            if (record.getFirstName().equals(person.getFirstName()) &&
	                record.getLastName().equals(person.getLastName())) {
	                return record; // Retourner l'enregistrement trouvé
	            }
	        }
	        return null; // Retourner null si aucun enregistrement n'a été trouvé
	    }


		//==============================
		// ENDPOINT /firestation
		// ==============================
		
		/**
		 * Ajoute un nouveau mapping entre une caserne et une adresse.
		 *
		 * @param address L'adresse à associer à la caserne.
		 * @param station Le numéro de la caserne.
		 * @return FireStations ajouté ou null si un mapping existe déjà.
		 */
		public FireStations addFireStationMapping(String address, int station) {
		    Optional<FireStations> existingMapping = myRepository.getFireStations()
		            .stream()
		            .filter(fs -> fs.getAddress().equalsIgnoreCase(address))
		            .findFirst();
		
		    if (existingMapping.isPresent()) {
		        return null; // Évite les doublons
		    }
		
		    FireStations newMapping = new FireStations();
		    myRepository.getFireStations().add(newMapping);
		    return newMapping;
		}
		
		/**
		 * Met à jour le numéro de la caserne pour une adresse donnée.
		 *
		 * @param address L'adresse concernée.
		 * @param newStation Le nouveau numéro de caserne.
		 * @return FireStations mis à jour ou null si l'adresse n'existe pas.
		 */
		public FireStations updateFireStationMapping(String address, String newStation) {
		    for (FireStations fs : myRepository.getFireStations()) {
		        if (fs.getAddress().equalsIgnoreCase(address)) {
		            fs.setStation(newStation);
		            return fs;
		        }
		    }
		    return null; // L'adresse n'existe pas
		}
		
		/**
		 * Supprime le mapping d'une adresse ou d'une caserne.
		 *
		 * @param address L'adresse à supprimer.
		 * @return true si supprimé, false si l'adresse n'existe pas.
		 */
		public boolean deleteFireStationMapping(String address) {
		    List<FireStations> updatedList = myRepository.getFireStations()
		            .stream()
		            .filter(fs -> !fs.getAddress().equalsIgnoreCase(address))
		            .collect(Collectors.toList());
		
		    if (updatedList.size() == myRepository.getFireStations().size()) {
		        return false; // Aucune suppression effectuée
		    }
		
		    myRepository.setFireStations(updatedList);
		    return true;
		}
	}