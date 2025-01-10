package com.openclassroom.api.service;

import com.openclassroom.api.model.MedicalRecord;
import com.openclassroom.api.model.Person;
import com.openclassroom.api.repository.MyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PersonInfoLastNameService {

	@Autowired
    private MyRepository myRepository;

    /**
     * Récupère les informations des personnes ayant un nom de famille spécifique.
     *
     * @param lastName Nom de famille des personnes à rechercher.
     * @return Une liste contenant les informations des personnes avec ce nom de famille, ou une liste vide si aucune correspondance n'est trouvée.
     */
    public List<Map<String, Object>> getPersonInfoByLastName(String lastName) {
        // Initialiser une liste pour stocker les informations des personnes correspondantes.
        List<Map<String, Object>> personInfoList = new ArrayList<>();

        // Parcourir la liste des personnes.
        for (Person person : myRepository.getPersons()) {
            // Vérifier si le nom de famille correspond (insensible à la casse).
            if (person.getLastName().equalsIgnoreCase(lastName)) {
                // Trouver le dossier médical de la personne.
                MedicalRecord medicalRecord = findMedicalRecordForPerson(person);

                // Si un dossier médical est trouvé, collecter les informations pertinentes.
                if (medicalRecord != null) {
                    Map<String, Object> personInfo = new HashMap<>();
                    personInfo.put("firstName", person.getFirstName()); // Prénom de la personne.
                    personInfo.put("lastName", person.getLastName()); // Nom de famille de la personne.
                    personInfo.put("address", person.getAddress()); // Adresse de la personne.
                    personInfo.put("age", medicalRecord.getAge()); // Calcul de l'âge à partir de la date de naissance.
                    personInfo.put("email", person.getEmail()); // Adresse email de la personne.
                    personInfo.put("medications", medicalRecord.getMedications()); // Liste des médicaments pris.
                    personInfo.put("allergies", medicalRecord.getAllergies()); // Liste des allergies connues.

                    // Ajouter les informations collectées à la liste finale.
                    personInfoList.add(personInfo);
                }
            }
        }

        // Retourner la liste des informations des personnes ayant le nom de famille donné.
        return personInfoList;
    }

    /**
     * Recherche le dossier médical correspondant à une personne spécifique.
     *
     * @param person La personne dont on cherche le dossier médical.
     * @return Le dossier médical correspondant, ou null si aucun n'est trouvé.
     */
    private MedicalRecord findMedicalRecordForPerson(Person person) {
        // Parcourir la liste des dossiers médicaux pour trouver une correspondance.
        return myRepository.getMedicalRecords().stream()
                // Filtrer sur la base du prénom et du nom (insensible à la casse).
                .filter(record -> record.getFirstName().equalsIgnoreCase(person.getFirstName()) &&
                        record.getLastName().equalsIgnoreCase(person.getLastName()))
                .findFirst() // Récupérer le premier dossier médical correspondant.
                .orElse(null); // Retourner null si aucun dossier n'est trouvé.
    }
}