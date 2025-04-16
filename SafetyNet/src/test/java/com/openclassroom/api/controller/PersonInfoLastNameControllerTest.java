package com.openclassroom.api.controller;

import com.openclassroom.api.service.PersonInfoLastNameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PersonInfoLastNameControllerTest {

    @Mock
    private PersonInfoLastNameService personInfoLastNameService;

    @InjectMocks
    private PersonInfoLastNameController personInfoLastNameController;

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PersonInfoLastNameControllerTest.class);

    @Test
    public void testGetPersonInfoByLastName_Success() {
        String lastName = "Doe";
        
        // Création d'une liste simulée d'informations
        Map<String, Object> personInfo = new HashMap<>();
        personInfo.put("firstName", "John");
        personInfo.put("lastName", "Doe");
        personInfo.put("address", "123 Street");
        personInfo.put("age", 30);
        personInfo.put("email", "john.doe@example.com");
        personInfo.put("medications", Arrays.asList("Medication1", "Medication2"));
        personInfo.put("allergies", Arrays.asList("Pollen", "Dust"));

        List<Map<String, Object>> personInfoList = Arrays.asList(personInfo);

        logger.info("Début du test : testGetPersonInfoByLastName_Success pour {}", lastName);
        System.out.println("Début du test : testGetPersonInfoByLastName_Success pour " + lastName);

        when(personInfoLastNameService.getPersonInfoByLastName(lastName)).thenReturn(personInfoList);

        // Appel de l'endpoint
        List<Map<String, Object>> response = personInfoLastNameController.getPersonInfoByLastName(lastName);

        // Vérification de la réponse
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("John", response.get(0).get("firstName"));
        assertEquals("Doe", response.get(0).get("lastName"));
        assertEquals("123 Street", response.get(0).get("address"));
        assertEquals(30, response.get(0).get("age"));
        assertEquals("john.doe@example.com", response.get(0).get("email"));

        // Log et System.out.print pour confirmer la fin du test
        logger.info("Fin du test : testGetPersonInfoByLastName_Success pour {}", lastName);
        System.out.println("Fin du test : testGetPersonInfoByLastName_Success pour " + lastName);

        // Vérification de l'appel du service
        verify(personInfoLastNameService, times(1)).getPersonInfoByLastName(lastName);
    }

    @Test
    public void testGetPersonInfoByLastName_NoResults() {
        String lastName = "Smith";

        logger.info("Début du test : testGetPersonInfoByLastName_NoResults pour {}", lastName);
        System.out.println("Début du test : testGetPersonInfoByLastName_NoResults pour " + lastName);

        // Aucun résultat pour ce nom
        when(personInfoLastNameService.getPersonInfoByLastName(lastName)).thenReturn(Arrays.asList());

        // Appel de l'endpoint
        List<Map<String, Object>> response = personInfoLastNameController.getPersonInfoByLastName(lastName);

        // Vérification de la réponse
        assertNotNull(response);
        assertTrue(response.isEmpty());

        // Log et System.out.print pour confirmer la fin du test
        logger.info("Fin du test : testGetPersonInfoByLastName_NoResults pour {}", lastName);
        System.out.println("Fin du test : testGetPersonInfoByLastName_NoResults pour " + lastName);

        // Vérification de l'appel du service
        verify(personInfoLastNameService, times(1)).getPersonInfoByLastName(lastName);
    }

    @Test
    public void testGetPersonInfoByLastName_EmptyLastName() {
        String lastName = "";

        logger.info("Début du test : testGetPersonInfoByLastName_EmptyLastName pour {}", lastName);
        System.out.println("Début du test : testGetPersonInfoByLastName_EmptyLastName pour " + lastName);

        // Test si une chaîne vide est transmise (cela pourrait renvoyer une liste vide ou une erreur, selon la logique)
        when(personInfoLastNameService.getPersonInfoByLastName(lastName)).thenReturn(Arrays.asList());

        // Appel de l'endpoint
        List<Map<String, Object>> response = personInfoLastNameController.getPersonInfoByLastName(lastName);

        // Vérification de la réponse
        assertNotNull(response);
        assertTrue(response.isEmpty());

        // Log et System.out.print pour confirmer la fin du test
        logger.info("Fin du test : testGetPersonInfoByLastName_EmptyLastName pour {}", lastName);
        System.out.println("Fin du test : testGetPersonInfoByLastName_EmptyLastName pour " + lastName);

        // Vérification de l'appel du service
        verify(personInfoLastNameService, times(1)).getPersonInfoByLastName(lastName);
    }
}
