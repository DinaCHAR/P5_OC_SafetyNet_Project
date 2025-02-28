package com.openclassroom.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ChildAlertService.class);

    @Autowired
    private MyRepository myRepository;

    /**
     * Cette méthode retourne la liste des enfants et des membres du foyer pour une adresse donnée.
     * @param address L'adresse pour laquelle on recherche les personnes.
     * @return Un objet ChildAlert contenant les enfants et membres du foyer.
     */
    public ChildAlert getChildrenByAddress(String address) {
        logger.info("Recherche des enfants et membres du foyer pour l'adresse : {}", address);

        List<Map<String, Object>> children = new ArrayList<>();
        List<Map<String, Object>> householdMembers = new ArrayList<>();

        // Trouver toutes les personnes vivant à l'adresse donnée
        for (Person person : myRepository.getPersons()) {
            if (person.getAddress().equalsIgnoreCase(address)) {
                logger.debug("Personne trouvée : {} {}", person.getFirstName(), person.getLastName());

                List<MedicalRecord> medicalRecords = (List<MedicalRecord>) findMedicalRecordForPerson(person);
                MedicalRecord medicalRecord = null;

                //CORRECTION !!!
                if (!medicalRecords.isEmpty()) {
                    medicalRecord = medicalRecords.get(0);
                    logger.debug("Dossier médical trouvé pour {} {}", person.getFirstName(), person.getLastName());
                } else {
                    logger.debug("Aucun dossier médical trouvé pour {} {}", person.getFirstName(), person.getLastName());
                    continue;
                }

                if (medicalRecord != null) {
                    int age = medicalRecord.getAge();
                    logger.debug("Âge de la personne : {}", age);

                    if (age <= 18) {  // Vérification que l'enfant est moins de 18 ans
                        Map<String, Object> childInfo = new HashMap<>();
                        childInfo.put("firstName", person.getFirstName());
                        childInfo.put("lastName", person.getLastName());
                        childInfo.put("age", age);
                        children.add(childInfo); // Ajouter à la liste des enfants
                        logger.info("Enfant ajouté : {} {}", person.getFirstName(), person.getLastName());
                    } else {  // Membres adultes du foyer
                        Map<String, Object> memberInfo = new HashMap<>();
                        memberInfo.put("firstName", person.getFirstName());
                        memberInfo.put("lastName", person.getLastName());
                        householdMembers.add(memberInfo);  // Ajouter à la liste des membres du foyer
                        logger.info("Membre adulte ajouté : {} {}", person.getFirstName(), person.getLastName());
                    }
                }
            }
        }

        // Créer un objet ChildAlert avec les informations collectées
        ChildAlert childAlert = new ChildAlert();
        childAlert.setChildren(children.isEmpty() ? null : children);  // Si aucun enfant, on retourne null
        childAlert.setHouseholdMembers(householdMembers.isEmpty() ? null : householdMembers);  // Idem pour les membres du foyer

        logger.info("Retour des résultats : {} enfants, {} membres du foyer", children.size(), householdMembers.size());

        return childAlert;
    }

    /**
     * Recherche du MedicalRecord pour une personne donnée.
     */
    private List<MedicalRecord> findMedicalRecordForPerson(Person person) {
        logger.debug("Recherche du dossier médical pour : {} {}", person.getFirstName(), person.getLastName());

        // Accède à la liste de tous les dossiers médicaux
        List<MedicalRecord> records = myRepository.getMedicalRecords().stream()

                // Filtre les dossiers médicaux pour trouver ceux qui correspondent exactement au prénom et au nom de la personne
                .filter(record -> 
                    record.getFirstName().equalsIgnoreCase(person.getFirstName()) && // Comparaison du prénom
                    record.getLastName().equalsIgnoreCase(person.getLastName()))    // Comparaison du nom de famille
                
                // Collecte tous les dossiers correspondants dans une liste
                .collect(Collectors.toList());

        logger.debug("Dossiers médicaux trouvés : {}", records.size());
        return records;
    }
}
