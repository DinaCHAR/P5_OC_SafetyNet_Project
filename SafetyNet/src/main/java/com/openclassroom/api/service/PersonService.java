package com.openclassroom.api.service;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassroom.api.model.Person;
import com.openclassroom.api.repository.MyRepository;

@Service
public class PersonService {

    @Autowired
    private MyRepository myRepository;

    /**
     * Ajoute une nouvelle personne dans le referentiel.
     * 
     * @param person La personne a ajouter.
     * @return true si la personne a ete ajoutee avec succes, false sinon.
     */
    public boolean addPerson(Person person) {
        // Verifie si une personne avec le meme prenom et nom existe deja
        boolean exists = myRepository.getPersons().stream()
            .anyMatch(existingPerson ->
                existingPerson.getFirstName().equalsIgnoreCase(person.getFirstName()) &&
                existingPerson.getLastName().equalsIgnoreCase(person.getLastName()));

        if (exists) {
            return false; // Empeche les doublons
        }

        myRepository.getPersons().add(person); // Ajout de la nouvelle personne
        return true;
    }

    /**
     * Met à jour les informations d'une personne existante dans le dépôt.
     *
     * @param updatedPerson L'objet Person contenant les donnees mises à jour.
     *                      Le prenom et le nom de famille servent d'identificateurs uniques
     *                      et ne peuvent pas être modifiés.
     * @return La personne mise à jour si la modification a réussi, ou null si aucune correspondance
     *         n'est trouvée.
     */
    public Person updatePerson(Person updatedPerson) {
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

            // Retourne l'objet Person mis à jour
            return existingPerson;
        }

        // Retourne null si la personne avec le prénom et le nom donnés n'a pas été trouvée
        return null;
    }


    /**
     * Supprime une personne en utilisant son prenom et son nom comme identifiant unique.
     * 
     * @param firstName Le prenom de la personne.
     * @param lastName  Le nom de famille de la personne.
     * @return true si la personne a ete supprimee avec succes, false sinon.
     */
    public boolean deletePerson(String firstName, String lastName) {
        // Recherche la personne et la supprime si elle existe
        return myRepository.getPersons().removeIf(person ->
            person.getFirstName().equalsIgnoreCase(firstName) &&
            person.getLastName().equalsIgnoreCase(lastName));
    }
}