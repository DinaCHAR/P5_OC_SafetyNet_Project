package com.openclassroom.api.service;

import com.openclassroom.api.model.Person;
import com.openclassroom.api.repository.MyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private MyRepository myRepository;

    @BeforeEach
    void setUp() {
    	myRepository.Init();
    }

    @Test
    void testAddPerson_Success() {
    	int nombreDePersonnes = myRepository.getPersons().size();

        Person person = new Person("Jane", "Doe", "456 Elm St", "Culver", "54321", "987-654-3210", "jane.doe@example.com");
        boolean result = personService.addPerson(person);

        // Vérifie que la personne a été ajoutée avec succès
        assertTrue(result);
        assertEquals(nombreDePersonnes +1, myRepository.getPersons().size());
    }

    @Test
    void testAddPerson_AlreadyExists() {
        Person person = new Person("Peter", "Duncan", "644 Gershwin Cir", "Culver", "97451", "841-874-6512", "jaboyd@email.com");
        boolean result = personService.addPerson(person);

        // Vérifie que l'ajout a échoué car la personne existe déjà
        assertFalse(result);
    }

    @Test
    void testUpdatePerson_Success() {
        Person updatedPerson = new Person("Peter", "Duncan", "908 73rd St", "New york", "97451", "841-874-6512", "jaboyd@email.com");
        Person result = personService.updatePerson(updatedPerson);

        // Vérifie que la mise à jour a réussi et que les informations sont modifiées
        assertNotNull(result);
        assertEquals("908 73rd St", result.getAddress());
        assertEquals("New york", result.getCity());
    }

    @Test
    void testUpdatePerson_NotFound() {
        Person updatedPerson = new Person("Jane", "Doe", "908 73rd St", "New york", "67890", "123-789-4560", "jane.doe@newdomain.com");
        Person result = personService.updatePerson(updatedPerson);

        // Vérifie que la mise à jour a échoué car la personne n'existe pas
        assertNull(result);
    }

    @Test
    void testDeletePerson_Success() {
    	int nombreDePersonnes = myRepository.getPersons().size();
        boolean result = personService.deletePerson("Peter", "Duncan");

        // Vérifie que la personne a été supprimée avec succès
        assertTrue(result);
        assertEquals(nombreDePersonnes -1, myRepository.getPersons().size());
    }

    @Test
    void testDeletePerson_NotFound() {
        boolean result = personService.deletePerson("Jane", "Doe");

        // Vérifie que la suppression a échoué car la personne n'existe pas
        assertFalse(result);
    }

    @Test
    void testGetPersons() {
        List<Person> persons = personService.getPersons();

        // Vérifie que la liste de personnes contient les données attendues
        assertEquals(23, persons.size());  // Seulement la personne "John Doe" doit être présente après le setUp
    }
}
