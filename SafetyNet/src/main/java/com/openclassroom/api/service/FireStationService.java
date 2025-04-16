package com.openclassroom.api.service;

import com.openclassroom.api.model.FireStation;
import com.openclassroom.api.model.MedicalRecord;
import com.openclassroom.api.model.Person;
import com.openclassroom.api.model.PersonByFirestation;
import com.openclassroom.api.repository.MyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FireStationService {

    private static final Logger logger = LoggerFactory.getLogger(FireStationService.class);

    @Autowired
    private MyRepository myRepository;

    /**
     * Fonction pour obtenir les personnes couvertes par une station de pompiers donnée.
     *
     * @param stationNumber Le numéro de la station de pompiers
     * @return Une Map contenant une liste de personnes couvertes, ainsi que le décompte des adultes et des enfants.
     */
    public PersonByFirestation getPersonsCoveredByStation(String stationNumber) {
        logger.info("Début de la récupération des personnes couvertes par la station de pompiers : {}", stationNumber);

        // Récupération de la liste des stations de pompiers à partir du dépôt
        List<FireStation> stations = myRepository.getFireStations();
        // Liste pour stocker les personnes couvertes par la station demandée
        List<Person> personsCovered = new ArrayList<>();
        // Compteurs pour le nombre d'adultes et d'enfants
        int adultCount = 0;
        int childCount = 0;

        // Parcourir toutes les stations de pompiers
        for (FireStation station : stations) {
            // Vérifier si le numéro de la station correspond à celui demandé
            if (station.getStation().equals(stationNumber)) {
                // Récupérer l'adresse associée à cette station
                String stationAddress = station.getAddress();
                logger.debug("Adresse associée à la station : {}", stationAddress);

                // Rechercher toutes les personnes dont l'adresse correspond à celle de la station
                for (Person person : myRepository.getPersons()) {
                    // Comparer les adresses
                	logger.debug("QUI EST LA : {}", person);
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

        logger.info("Nombre d'enfants : {}, Nombre d'adultes : {}", childCount, adultCount);

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
        logger.debug("Recherche de l'enregistrement médical pour : {} {}", person.getFirstName(), person.getLastName());

        // Parcourir tous les enregistrements médicaux disponibles
        for (MedicalRecord record : myRepository.getMedicalRecords()) {
            // Vérifier si le prénom et le nom correspondent
            if (record.getFirstName().equals(person.getFirstName()) &&
                record.getLastName().equals(person.getLastName())) {
                logger.debug("Enregistrement médical trouvé pour : {} {}", person.getFirstName(), person.getLastName());
                return record; // Retourner l'enregistrement trouvé
            }
        }

        logger.warn("Aucun enregistrement médical trouvé pour : {} {}", person.getFirstName(), person.getLastName());
        return null; // Retourner null si aucun enregistrement n'a été trouvé
    }

    // ==============================
    // ENDPOINT /firestation
    // ==============================

    /**
     * Ajoute un nouveau mapping entre une caserne et une adresse.
     *
     * @param address L'adresse à associer à la caserne.
     * @param station Le numéro de la caserne.
     * @return FireStations ajouté ou null si un mapping existe déjà.
     */
    public FireStation addFireStationMapping(String address, int station) {
        logger.info("Tentative d'ajout d'une nouvelle caserne pour l'adresse : {}", address);

        Optional<FireStation> existingMapping = myRepository.getFireStations()
                .stream()
                .filter(fs -> fs.getAddress().equalsIgnoreCase(address))
                .findFirst();

        if (existingMapping.isPresent()) {
            logger.warn("Mapping déjà existant pour l'adresse : {}", address);
            return null; // Évite les doublons
        }

        FireStation newMapping = new FireStation();
        myRepository.getFireStations().add(newMapping);
        logger.info("Nouveau mapping ajouté pour l'adresse : {}", address);

        return newMapping;
    }

    /**
     * Met à jour le numéro de la caserne pour une adresse donnée.
     *
     * @param address L'adresse concernée.
     * @param newStation Le nouveau numéro de caserne.
     * @return FireStations mis à jour ou null si l'adresse n'existe pas.
     */
    public FireStation updateFireStationMapping(String address, String newStation) {
        logger.info("Tentative de mise à jour de la station pour l'adresse : {}", address);

        for (FireStation fs : myRepository.getFireStations()) {
            if (fs.getAddress().equalsIgnoreCase(address)) {
                fs.setStation(newStation);
                logger.info("Station mise à jour pour l'adresse : {}", address);
                return fs;
            }
        }

        logger.warn("Aucune station trouvée pour l'adresse : {}", address);
        return null; // L'adresse n'existe pas
    }

    /**
     * Supprime le mapping d'une adresse ou d'une caserne.
     *
     * @param address L'adresse à supprimer.
     * @return true si supprimé, false si l'adresse n'existe pas.
     */
    public boolean deleteFireStationMapping(String address) {
        logger.info("Tentative de suppression du mapping pour l'adresse : {}", address);

        List<FireStation> updatedList = myRepository.getFireStations()
                .stream()
                .filter(fs -> fs.getAddress() != null && !fs.getAddress().equalsIgnoreCase(address))
                .collect(Collectors.toList());

        if (updatedList.size() == myRepository.getFireStations().size()) {
            logger.warn("Aucun mapping trouvé pour l'adresse : {}", address);
            return false; // Aucune suppression effectuée
        }

        myRepository.setFireStations(updatedList);
        logger.info("Mapping supprimé pour l'adresse : {}", address);

        return true;
    }
}
