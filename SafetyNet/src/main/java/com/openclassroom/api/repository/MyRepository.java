package com.openclassroom.api.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassroom.api.SafetyNetApplication;
import com.openclassroom.api.model.FireStation;
import com.openclassroom.api.model.MedicalRecord;
import com.openclassroom.api.model.Person;

@Repository
public class MyRepository {

	// Méthode principale pour démarrer l'application
	public static void main(String[] args) {
	    // Démarrer l'application Spring Boot en utilisant la classe SafetyNetApplication
	    SpringApplication.run(SafetyNetApplication.class, args);

	    // Création d'une instance de myRepo et appel de la méthode Init pour charger les données depuis le fichier JSON
	    MyRepository repo = new MyRepository();
	    repo.Init();
	}
	
	//CREE DES VAR POUR RECUP DES LISTE DE PERSONNES
	 List<Person> persons = null;
	 List<MedicalRecord> medicalRecords = null;
	 List<FireStation> fireStations = null;

	// Méthode pour initialiser le chargement des données
    public void Init() {
        // Chargement du fichier JSON
        InputStream is = getClass().getClassLoader().getResourceAsStream("data.json");

        if (is == null) {
            System.out.println("Le fichier data.json est introuvable !");
            return;
        }

        // ObjectMapper convertir des objet java en json
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Lire le fichier JSON comme un objet générique (JsonNode)
            JsonNode rootNode = objectMapper.readTree(is);
            
         // Récupérer le tableau sous la clé "persons"
            JsonNode personsNode = rootNode.path("persons");
            if (personsNode.isArray()) {
                this.persons = objectMapper.readValue(personsNode.toString(), new TypeReference<List<Person>>() {});
                for (Person person : persons) {
                    System.out.println(person);
                }
            }

            // Récupérer le tableau sous la clé "medicalrecords"
            JsonNode medicalRecordsNode = rootNode.path("medicalrecords");
            if (medicalRecordsNode.isArray()) {
                this.medicalRecords = objectMapper.readValue(medicalRecordsNode.toString(), new TypeReference<List<MedicalRecord>>() {});
                for (MedicalRecord record : medicalRecords) {
                    System.out.println(record);
                }
            }

            // Récupérer le tableau sous la clé "firestations"
            JsonNode fireStationsNode = rootNode.path("firestations");
            if (fireStationsNode.isArray()) {
                this.fireStations = objectMapper.readValue(fireStationsNode.toString(), new TypeReference<List<FireStation>>() {});
                for (FireStation station : fireStations) {
                    System.out.println(station);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public List<Person> getPersons() {
		if(persons == null) {
			Init();
		}
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public List<MedicalRecord> getMedicalRecords() {
		if(medicalRecords == null) {
			Init();
		}
		return medicalRecords;
	}

	public void setMedicalRecords(List<MedicalRecord> medicalRecords) {
		this.medicalRecords = medicalRecords;
	}

	public List<FireStation> getFireStations() {
		if(fireStations == null) {
			Init();
		}
		return fireStations;
	}

	public void setFireStations(List<FireStation> fireStations) {
		this.fireStations = fireStations;
	}

	
	
	public void addPerson(Person person) {
	    if (persons == null) {
	        Init();
	    }
	    persons.add(person);
	}

	public void addFireStation(FireStation fireStation) {
	    if (fireStations == null) {
	        Init();
	    }
	    fireStations.add(fireStation);
	}

	public void clearData() {
	    if (persons != null) {
	        persons.clear();
	    }
	    if (fireStations != null) {
	        fireStations.clear();
	    }
	}
}