package com.openclassroom.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassroom.api.model.MedicalRecord;
import com.openclassroom.api.model.Person;
import com.openclassroom.api.repository.MyRepository;

@Service
public class FloodService {

    private static final Logger logger = LoggerFactory.getLogger(FloodService.class);

    @Autowired
    private MyRepository myRepository;

    /**
     * Cette méthode retourne une liste des foyers (regroupés par adresse) desservis par les casernes spécifiées.
     *
     * @param stationNumbers Liste des numéros des casernes de pompiers.
     * @return Un mapping où chaque clé est une adresse et chaque valeur est une liste des informations des habitants.
     */
    public Map<String, List<Map<String, Object>>> getHouseholdsByStations(List<Integer> stationNumbers) {
        logger.info("Début de la récupération des foyers desservis par les casernes : {}", stationNumbers);

        // Étape 1 : Identifier les adresses desservies par les casernes données.
        // Récupère les adresses des casernes correspondant aux numéros de stations fournis
        Set<String> addresses = myRepository.getFireStations().stream()
            .filter(fs -> stationNumbers.contains(Integer.valueOf(fs.getStation()))) 
            .map(fs -> fs.getAddress())
            .collect(Collectors.toSet());

        logger.debug("Adresses des casernes trouvées : {}", addresses);

        // Initialise une structure de données pour stocker les foyers groupés par adresse
        Map<String, List<Map<String, Object>>> households = new HashMap<>();

        // Étape 2 : Itérer sur les adresses récupérées pour collecter les informations des habitants.
        for (String address : addresses) {
            logger.debug("Traitement des habitants à l'adresse : {}", address);

            // Trouver toutes les personnes vivant à cette adresse.
            List<Person> personsAtAddress = myRepository.getPersons().stream()
                    .filter(person -> person.getAddress().equalsIgnoreCase(address))
                    .collect(Collectors.toList());

            // Liste pour stocker les informations des habitants
            List<Map<String, Object>> residentsInfo = new ArrayList<>();

            // Étape 3 : Collecter les informations pour chaque habitant à cette adresse.
            for (Person person : personsAtAddress) {
                logger.debug("Collecte des informations pour la personne : {} {}", person.getFirstName(), person.getLastName());

                Map<String, Object> residentData = new HashMap<>();
                residentData.put("firstName", person.getFirstName());
                residentData.put("lastName", person.getLastName());
                residentData.put("phone", person.getPhone());

                // Récupérer le dossier médical de la personne
                MedicalRecord medicalRecord = findMedicalRecordForPerson(person);
                if (medicalRecord != null) {
                    residentData.put("age", medicalRecord.getAge());
                    residentData.put("medications", medicalRecord.getMedications());
                    residentData.put("allergies", medicalRecord.getAllergies());
                } else {
                    logger.warn("Aucun dossier médical trouvé pour la personne : {} {}", person.getFirstName(), person.getLastName());
                }

                // Ajouter les informations collectées pour cette personne
                residentsInfo.add(residentData);
            }

            // Associer les informations des habitants à leur adresse
            households.put(address, residentsInfo);
            logger.debug("Foyer ajouté pour l'adresse : {} avec {} résidents.", address, residentsInfo.size());
        }

        logger.info("Récupération des foyers terminée, nombre total de foyers : {}", households.size());

        // Retourner le mapping final des foyers par adresse
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
        logger.debug("Recherche du dossier médical pour : {} {}", person.getFirstName(), person.getLastName());

        // Rechercher dans la liste des dossiers médicaux un enregistrement correspondant au prénom et au nom
        return myRepository.getMedicalRecords().stream()
                .filter(record ->
                    record.getFirstName().equalsIgnoreCase(person.getFirstName()) &&
                    record.getLastName().equalsIgnoreCase(person.getLastName()))
                .findFirst()
                .orElse(null); // Retourne null si aucun dossier n'est trouvé
    }
}
