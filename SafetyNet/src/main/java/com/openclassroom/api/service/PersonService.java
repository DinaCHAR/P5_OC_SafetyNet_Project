package com.openclassroom.api.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassroom.api.model.Person;
import com.openclassroom.api.repository.MyRepository;

@Service
public class PersonService {

    private static final Logger logger = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    private MyRepository myRepository;

    /**
     * Ajoute une nouvelle personne dans le référentiel.
     * 
     * @param person La personne à ajouter.
     * @return true si la personne a été ajoutée avec succès, false sinon.
     */
    public boolean addPerson(Person person) {
        logger.info("Tentative d'ajouter la personne : {} {}", person.getFirstName(), person.getLastName());

        // Vérifie si une personne avec le même prénom et nom existe déjà
        boolean exists = myRepository.getPersons().stream()
            .anyMatch(existingPerson ->
                existingPerson.getFirstName().equalsIgnoreCase(person.getFirstName()) &&
                existingPerson.getLastName().equalsIgnoreCase(person.getLastName()));

        if (exists) {
            logger.warn("La personne {} {} existe déjà dans le référentiel. Ajout échoué.", person.getFirstName(), person.getLastName());
            return false; // Empêche les doublons
        }

        myRepository.getPersons().add(person); // Ajout de la nouvelle personne
        logger.info("La personne {} {} a été ajoutée avec succès.", person.getFirstName(), person.getLastName());
        return true;
    }

    /**
     * Met à jour les informations d'une personne existante dans le dépôt.
     *
     * @param updatedPerson L'objet Person contenant les données mises à jour.
     *                      Le prénom et le nom de famille servent d'identificateurs uniques
     *                      et ne peuvent pas être modifiés.
     * @return La personne mise à jour si la modification a réussi, ou null si aucune correspondance
     *         n'est trouvée.
     */
    public Person updatePerson(Person updatedPerson) {
        logger.info("Tentative de mise à jour de la personne : {} {}", updatedPerson.getFirstName(), updatedPerson.getLastName());

        // Recherche la personne existante dans le dépôt par prénom et nom de famille
        java.util.Optional<Person> existingPersonOpt = myRepository.getPersons().stream()
            .filter(person ->
                person.getFirstName().equalsIgnoreCase(updatedPerson.getFirstName()) &&
                person.getLastName().equalsIgnoreCase(updatedPerson.getLastName()))
            .findFirst();

        if (existingPersonOpt.isPresent()) {
            // Récupère l'objet de la personne existante
            Person existingPerson = existingPersonOpt.get();

            // Met à jour les informations de la personne existante avec les nouvelles données
            existingPerson.setAddress(updatedPerson.getAddress());
            existingPerson.setCity(updatedPerson.getCity());
            existingPerson.setZip(updatedPerson.getZip());
            existingPerson.setPhone(updatedPerson.getPhone());
            existingPerson.setEmail(updatedPerson.getEmail());

            logger.info("La personne {} {} a été mise à jour avec succès.", existingPerson.getFirstName(), existingPerson.getLastName());
            // Retourne l'objet Person mis à jour
            return existingPerson;
        }

        logger.warn("Aucune personne trouvée pour la mise à jour avec le nom : {} {}", updatedPerson.getFirstName(), updatedPerson.getLastName());
        // Retourne null si la personne avec le prénom et le nom donnés n'a pas été trouvée
        return null;
    }

    /**
     * Supprime une personne en utilisant son prénom et son nom comme identifiant unique.
     * 
     * @param firstName Le prénom de la personne.
     * @param lastName  Le nom de famille de la personne.
     * @return true si la personne a été supprimée avec succès, false sinon.
     */
    public boolean deletePerson(String firstName, String lastName) {
        logger.info("Tentative de suppression de la personne : {} {}", firstName, lastName);

        // Recherche la personne et la supprime si elle existe
        boolean removed = myRepository.getPersons().removeIf(person ->
            person.getFirstName().equalsIgnoreCase(firstName) &&
            person.getLastName().equalsIgnoreCase(lastName));

        if (removed) {
            logger.info("La personne {} {} a été supprimée avec succès.", firstName, lastName);
        } else {
            logger.warn("Aucune personne trouvée pour la suppression avec le nom : {} {}", firstName, lastName);
        }

        return removed;
    }

    public List<Person> getPersons() {
        return myRepository.getPersons();
    }
}
