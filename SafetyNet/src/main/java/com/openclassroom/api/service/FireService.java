package com.openclassroom.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassroom.api.model.FireStation;
import com.openclassroom.api.model.MedicalRecord;
import com.openclassroom.api.model.Person;
import com.openclassroom.api.model.PersonByFirestation;
import com.openclassroom.api.repository.MyRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class FireService {

    private static final Logger logger = LoggerFactory.getLogger(FireService.class);

    @Autowired
    private MyRepository myRepository;

    /**
     * Récupérer les personnes vivant à une adresse donnée.
     *
     * @param address L'adresse cible.
     * @return Un objet `PersonByFirestation` contenant les personnes, les enfants, les adultes et le numéro de station.
     */
    public PersonByFirestation getPersonsByAddress(String address) {
        logger.info("Début de la récupération des personnes pour l'adresse : {}", address);

        // Initialisation du résultat
        PersonByFirestation result = new PersonByFirestation();
        List<Person> personsAtAddress = new ArrayList<>();
        int childCount = 0;
        int adultCount = 0;

        // Parcourir la liste des personnes pour celles qui habitent à l'adresse donnée
        for (Person person : myRepository.getPersons()) {
            if (person.getAddress().equalsIgnoreCase(address)) {
                personsAtAddress.add(person);

                // Récupérer l'enregistrement médical pour calculer l'âge
                MedicalRecord medicalRecord = findMedicalRecordForPerson(person);
                if (medicalRecord != null) {
                    int age = medicalRecord.getAge();
                    if (age <= 18) {
                        childCount++;
                    } else {
                        adultCount++;
                    }
                }
            }
        }

        // Mise à jour du résultat avec les données récupérées
        result.setPersons(personsAtAddress);
        result.setChildCount(childCount);
        result.setAdultCount(adultCount);

        // Récupérer la caserne des pompiers associée à l'adresse
        FireStation fireStation = myRepository.getFireStations().stream()
                .filter(station -> station.getAddress().equalsIgnoreCase(address))
                .findFirst()
                .orElse(null);
        if (fireStation != null) {
            result.setPersonsAtAddress(Integer.parseInt(fireStation.getStation())); // Stocker la station dans le champ approprié
        }

        logger.info("Fin de la récupération des personnes pour l'adresse : {}", address);
        logger.info("Nombre d'enfants : {}, Nombre d'adultes : {}", childCount, adultCount);

        // Retourner le résultat
        return result;
    }

    /**
     * Trouver l'enregistrement médical associé à une personne.
     *
     * @param person La personne pour laquelle l'enregistrement médical est recherché.
     * @return Un objet `MedicalRecord` correspondant ou null si aucun n'est trouvé.
     */
    private MedicalRecord findMedicalRecordForPerson(Person person) {
        return myRepository.getMedicalRecords().stream()
                .filter(record -> record.getFirstName().equals(person.getFirstName())
                        && record.getLastName().equals(person.getLastName()))
                .findFirst()
                .orElse(null);
    }
}
