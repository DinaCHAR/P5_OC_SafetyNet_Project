package com.openclassroom.api.controller;

import com.openclassroom.api.model.Person;
import com.openclassroom.api.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PersonControllerTest.class);

    @Test
    public void testAddPerson_Success() {
        Person person = new Person("John", "Doe", "123 Street");

        logger.info("Début du test : testAddPerson_Success pour {}", person);
        System.out.println("Début du test : testAddPerson_Success pour " + person);

        when(personService.addPerson(person)).thenReturn(true);

        // Appel de l'endpoint
        ResponseEntity<String> response = personController.addPerson(person);

        // Vérification de la réponse
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Person successfully added.", response.getBody());

        // Log et System.out.print pour confirmer la fin du test
        logger.info("Fin du test : testAddPerson_Success pour {}", person);
        System.out.println("Fin du test : testAddPerson_Success pour " + person);

        // Vérification de l'appel du service
        verify(personService, times(1)).addPerson(person);
    }

    @Test
    public void testAddPerson_Failure() {
        Person person = new Person("John", "Doe", "123 Street");

        logger.info("Début du test : testAddPerson_Failure pour {}", person);
        System.out.println("Début du test : testAddPerson_Failure pour " + person);

        when(personService.addPerson(person)).thenReturn(false);

        // Appel de l'endpoint
        ResponseEntity<String> response = personController.addPerson(person);

        // Vérification de la réponse
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Person already exists.", response.getBody());

        // Log et System.out.print pour confirmer la fin du test
        logger.info("Fin du test : testAddPerson_Failure pour {}", person);
        System.out.println("Fin du test : testAddPerson_Failure pour " + person);

        // Vérification de l'appel du service
        verify(personService, times(1)).addPerson(person);
    }

    @Test
    public void testUpdatePerson_Success() {
        Person updatedPerson = new Person("John", "Doe", "456 New Street", "City", "12345", "123-456-7890", "john.doe@example.com");

        logger.info("Début du test : testUpdatePerson_Success pour {}", updatedPerson);
        System.out.println("Début du test : testUpdatePerson_Success pour " + updatedPerson);

        when(personService.updatePerson(updatedPerson)).thenReturn(updatedPerson);

        // Appel de l'endpoint
        ResponseEntity<String> response = personController.updatePerson(updatedPerson);

        // Vérification de la réponse
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Person successfully updated.", response.getBody());

        // Log et System.out.print pour confirmer la fin du test
        logger.info("Fin du test : testUpdatePerson_Success pour {}", updatedPerson);
        System.out.println("Fin du test : testUpdatePerson_Success pour " + updatedPerson);

        // Vérification de l'appel du service
        verify(personService, times(1)).updatePerson(updatedPerson);
    }

    @Test
    public void testUpdatePerson_Failure() {
        Person updatedPerson = new Person("John", "Doe", "456 New Street", "City", "12345", "123-456-7890", "john.doe@example.com");

        logger.info("Début du test : testUpdatePerson_Failure pour {}", updatedPerson);
        System.out.println("Début du test : testUpdatePerson_Failure pour " + updatedPerson);

        when(personService.updatePerson(updatedPerson)).thenReturn(null);

        // Appel de l'endpoint
        ResponseEntity<String> response = personController.updatePerson(updatedPerson);

        // Vérification de la réponse
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Person not found.", response.getBody());

        // Log et System.out.print pour confirmer la fin du test
        logger.info("Fin du test : testUpdatePerson_Failure pour {}", updatedPerson);
        System.out.println("Fin du test : testUpdatePerson_Failure pour " + updatedPerson);

        // Vérification de l'appel du service
        verify(personService, times(1)).updatePerson(updatedPerson);
    }

    @Test
    public void testDeletePerson_Success() {
        String firstName = "John";
        String lastName = "Doe";

        logger.info("Début du test : testDeletePerson_Success pour {} {}", firstName, lastName);
        System.out.println("Début du test : testDeletePerson_Success pour " + firstName + " " + lastName);

        when(personService.deletePerson(firstName, lastName)).thenReturn(true);

        // Appel de l'endpoint
        ResponseEntity<String> response = personController.deletePerson(firstName, lastName);

        // Vérification de la réponse
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Person successfully deleted.", response.getBody());

        // Log et System.out.print pour confirmer la fin du test
        logger.info("Fin du test : testDeletePerson_Success pour {} {}", firstName, lastName);
        System.out.println("Fin du test : testDeletePerson_Success pour " + firstName + " " + lastName);

        // Vérification de l'appel du service
        verify(personService, times(1)).deletePerson(firstName, lastName);
    }

    @Test
    public void testDeletePerson_Failure() {
        String firstName = "John";
        String lastName = "Doe";

        logger.info("Début du test : testDeletePerson_Failure pour {} {}", firstName, lastName);
        System.out.println("Début du test : testDeletePerson_Failure pour " + firstName + " " + lastName);

        when(personService.deletePerson(firstName, lastName)).thenReturn(false);

        // Appel de l'endpoint
        ResponseEntity<String> response = personController.deletePerson(firstName, lastName);

        // Vérification de la réponse
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Person not found.", response.getBody());

        // Log et System.out.print pour confirmer la fin du test
        logger.info("Fin du test : testDeletePerson_Failure pour {} {}", firstName, lastName);
        System.out.println("Fin du test : testDeletePerson_Failure pour " + firstName + " " + lastName);

        // Vérification de l'appel du service
        verify(personService, times(1)).deletePerson(firstName, lastName);
    }

    @Test
    public void testGetPersons() {
        List<Person> personList = Arrays.asList(
            new Person("John", "Doe", "123 Street"),
            new Person("Jane", "Doe", "456 Avenue")
        );

        logger.info("Début du test : testGetPersons");
        System.out.println("Début du test : testGetPersons");

        when(personService.getPersons()).thenReturn(personList);

        // Appel de l'endpoint
        List<Person> response = personController.getPersons();

        // Vérification de la réponse
        assertNotNull(response);
        assertEquals(2, response.size());

        // Log et System.out.print pour confirmer la fin du test
        logger.info("Fin du test : testGetPersons");
        System.out.println("Fin du test : testGetPersons");

        // Vérification de l'appel du service
        verify(personService, times(1)).getPersons();
    }
}
