package com.openclassroom.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassroom.api.model.Person;
import com.openclassroom.api.repository.MyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommunityEmailService {

    @Autowired
    private MyRepository myRepository;

    /**
     * Méthode pour récupérer les adresses email des habitants d'une ville donnée.
     *
     * @param city La ville cible
     * @return La liste des adresses email des habitants de cette ville
     */
    public List<String> getEmailsByCity(String city) {
        // Filtrer les habitants par ville et récupérer les emails sans doublons
        return myRepository.getPersons().stream()
                .filter(person -> person.getCity().equalsIgnoreCase(city)) // Comparaison insensible à la casse
                .map(Person::getEmail) // Récupérer l'email de chaque habitant
                .distinct() // Supprimer les doublons (si plusieurs personnes partagent le même email)
                .collect(Collectors.toList()); // Convertir en liste
    }
}
