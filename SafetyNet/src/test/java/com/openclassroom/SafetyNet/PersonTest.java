package com.openclassroom.SafetyNet;

import com.openclassroom.api.model.Person;
import com.openclassroom.api.service.PersonService;
import com.openclassroom.api.repository.MyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PersonTest {

    @Mock
    private MyRepository myRepository;

    @InjectMocks
    private PersonService personService;

    private List<Person> personList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        personList = new ArrayList<>();
        when(myRepository.getPersons()).thenReturn(personList);
        System.out.println("Initialisation des données : " + personList);
    }

    @Test
    public void testAddPersonSuccess() {
        Person person = new Person("John", "Doe", "123 Main St", "Springfield", "john.doe@example.com");
        System.out.println("Ajout de la personne : " + person);

        boolean result = personService.addPerson(person);

        assertTrue(result);
        assertEquals(1, personList.size());
        assertEquals("John", personList.get(0).getFirstName());

        System.out.println("Personne ajoutée avec succès : " + personList.get(0));
    }

    @Test
    public void testAddPersonDuplicate() {
        Person person = new Person("Jane", "Doe", "123 Main St", "Springfield", "jane.doe@example.com");
        personList.add(person);
        System.out.println("Personne déjà existante : " + person);

        boolean result = personService.addPerson(person);

        assertFalse(result);
        assertEquals(1, personList.size());

        System.out.println("Tentative d'ajout d'une personne dupliquée, résultat : " + result);
    }

    @Test
    public void testUpdatePersonSuccess() {
        Person existingPerson = new Person("John", "Doe", "123 Main St", "Springfield", "john.doe@example.com");
        personList.add(existingPerson);
        System.out.println("Personne existante avant mise à jour : " + existingPerson);

        Person updatedPerson = new Person("John", "Doe", "456 Elm St", "Springfield", "john.doe@example.com");
        Person result = personService.updatePerson(updatedPerson);

        assertNotNull(result);
        assertEquals("456 Elm St", result.getAddress());

        System.out.println("Personne mise à jour : " + result);
    }

    @Test
    public void testUpdatePersonNotFound() {
        Person updatedPerson = new Person("NonExistent", "Person", "456 Elm St", "Springfield", "nonexistent@example.com");
        System.out.println("Tentative de mise à jour d'une personne inexistante : " + updatedPerson);

        Person result = personService.updatePerson(updatedPerson);

        assertNull(result);

        System.out.println("Aucune personne trouvée pour la mise à jour.");
    }

    @Test
    public void testDeletePersonSuccess() {
        Person person = new Person("John", "Doe", "123 Main St", "Springfield", "john.doe@example.com");
        personList.add(person);
        System.out.println("Personne avant suppression : " + person);

        boolean result = personService.deletePerson("John", "Doe");

        assertTrue(result);
        assertTrue(personList.isEmpty());

        System.out.println("Personne supprimée avec succès.");
    }

    @Test
    public void testDeletePersonNotFound() {
        System.out.println("Tentative de suppression d'une personne inexistante.");

        boolean result = personService.deletePerson("Jane", "Doe");

        assertFalse(result);

        System.out.println("Aucune personne trouvée pour la suppression.");
    }
}
