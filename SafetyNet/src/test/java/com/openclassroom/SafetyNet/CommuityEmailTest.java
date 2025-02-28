package com.openclassroom.SafetyNet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.openclassroom.api.model.Person;
import com.openclassroom.api.repository.MyRepository;
import com.openclassroom.api.service.CommunityEmailService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class CommuityEmailTest {

    @MockBean
    private MyRepository myRepository;

    @Autowired
    private CommunityEmailService communityEmailService;

    @Test
    public void testGetEmailsByCity() {
        // Données mockées
        List<Person> mockPersons = new ArrayList<>();
        mockPersons.add(new Person("John", "Bin", "123 Main St", "Paris", "john.doe@email.com"));
        mockPersons.add(new Person("Jane", "Doe", "45 Rue Louvre", "Paris", "jane.doe@email.com"));
        mockPersons.add(new Person("Laure", "Beam", "1 bd Stalingrad", "Lyon", "Laure.beam@email.com"));

        //Affichage des données mockées
        System.out.println("Données mockées : ");
        mockPersons.forEach(person -> System.out.println(person.toString()));
        
        // Simulation du comportement de MyRepository
        when(myRepository.getPersons()).thenReturn(mockPersons);

        // Appel de la méthode à tester
        List<String> result = communityEmailService.getEmailsByCity("Paris");

        //Affichage des résultats récupérés
        System.out.println("Emails récupérés pour Paris : ");
        result.forEach(System.out::println);
        
        // Vérifications
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("john.doe@email.com"));
        assertTrue(result.contains("jane.doe@email.com"));
    }
}
