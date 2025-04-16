package com.openclassroom.api.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassroom.api.model.FireStation;
import com.openclassroom.api.model.Person;
import com.openclassroom.api.model.PersonByFirestation;
import com.openclassroom.api.repository.MyRepository;

@SpringBootTest
public class FireStationServiceTest {

    @Autowired
    private FireStationService fireStationService;

    @Autowired
    private MyRepository myRepository;

    @BeforeEach
    void setUp() {
    	
    	myRepository.Init();

    }

    @Test
    void testGetPersonsCoveredByStation_WithExistingStation() {
        PersonByFirestation result = fireStationService.getPersonsCoveredByStation("2");

        assertNotNull(result, "L'objet retourné ne doit pas être null");
        assertFalse(result.getPersons().isEmpty(), "La liste des personnes ne doit pas être vide");
        assertEquals(5, result.getPersons().size(), "Il doit y avoir 5 personne couverte");
    }

    @Test
    void testGetPersonsCoveredByStation_WithNonExistingStation() {
        PersonByFirestation result = fireStationService.getPersonsCoveredByStation("88");

        assertNotNull(result, "L'objet retourné ne doit pas être null");
        assertTrue(result.getPersons().isEmpty(), "La liste des personnes doit être vide");
    }
}
