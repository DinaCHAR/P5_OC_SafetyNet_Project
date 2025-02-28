package com.openclassroom.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassroom.api.model.Person;
import com.openclassroom.api.repository.MyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommunityEmailService {

    private static final Logger logger = LoggerFactory.getLogger(CommunityEmailService.class);

    @Autowired
    private MyRepository myRepository;

    /**
     * Méthode pour récupérer les adresses email des habitants d'une ville donnée.
     *
     * @param city La ville cible
     * @return La liste des adresses email des habitants de cette ville
     */
    public List<String> getEmailsByCity(String city) {
        logger.info("Recherche des emails pour la ville : {}", city);

        // Filtrer les habitants par ville et récupérer les emails sans doublons
        List<String> emails = myRepository.getPersons().stream()
                .filter(person -> person.getCity().equalsIgnoreCase(city)) // Comparaison insensible à la casse
                .map(Person::getEmail) // Récupérer l'email de chaque habitant
                .distinct() // Supprimer les doublons (si plusieurs personnes partagent le même email)
                .collect(Collectors.toList()); // Convertir en liste

        logger.info("Nombre d'emails trouvés pour la ville {} : {}", city, emails.size());

        return emails;
    }
}
