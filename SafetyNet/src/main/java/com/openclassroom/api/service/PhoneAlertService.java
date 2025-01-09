package com.openclassroom.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassroom.api.model.FireStations;
import com.openclassroom.api.model.Person;
import com.openclassroom.api.model.PersonByFirestation;
import com.openclassroom.api.model.PhoneByFireStation;
import com.openclassroom.api.repository.MyRepository;

@Service
public class PhoneAlertService {

    @Autowired
    private MyRepository myRepository;

    /**
     * Méthode pour obtenir les numéros de téléphone des habitants desservis 
     * par une caserne de pompiers donnée.
     *
     * @param firestation_number Le numéro de la caserne de pompiers
     * @return Un objet PhoneByFireStation contenant la liste des numéros de téléphone
     */
    public PhoneByFireStation getPhoneNumbersByFirestation(String firestation_number) {
        // Récupération de toutes les personnes et casernes de pompiers
        List<Person> persons = myRepository.getPersons();
        List<FireStations> fireStations = myRepository.getFireStations();

        // Étape 1 : Trouver toutes les adresses associées à la caserne spécifiée
        List<String> addresses = fireStations.stream()
            .filter(fs -> fs.getStation().equals(firestation_number)) // Filtrer par numéro de caserne
            .map(FireStations::getAddress) // Obtenir l'adresse de chaque caserne filtrée
            .collect(Collectors.toList());

        // Étape 2 : Récupérer les numéros de téléphone des personnes vivant à ces adresses
        List<String> phoneNumbers = persons.stream()
            .filter(p -> addresses.contains(p.getAddress())) // Filtrer les personnes par adresse
            .map(Person::getPhone) // Extraire le numéro de téléphone
            .collect(Collectors.toList());

        // Étape 3 : Créer et remplir un objet PhoneByFireStation
        PhoneByFireStation result = new PhoneByFireStation();
        result.setPhoneNumbers(phoneNumbers); // Ajouter la liste des numéros de téléphone
        
        return result;
    }
}