package com.openclassroom.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassroom.api.model.FireStation;
import com.openclassroom.api.model.Person;
import com.openclassroom.api.model.PhoneByFireStation;
import com.openclassroom.api.repository.MyRepository;

@SpringBootTest
public class PhoneAlertServiceTest {

    @Autowired
    private MyRepository myRepository;

    @Autowired
    private PhoneAlertService phoneAlertService;

    @BeforeEach
    void setUp() {
    	myRepository.Init();
    }

    @Test
    void testGetPhoneNumbersByFirestation_Success() {
        // Cas de test pour une caserne ayant des personnes desservies
        PhoneByFireStation result = phoneAlertService.getPhoneNumbersByFirestation("1");

        assertNotNull(result);
        assertEquals(6, result.getPhoneNumbers().size());
        assertTrue(result.getPhoneNumbers().contains("841-874-6512"));
    }

    @Test
    void testGetPhoneNumbersByFirestation_NoPersonsForFirestation() {
        // Cas de test pour une caserne sans personne desservie
        PhoneByFireStation result = phoneAlertService.getPhoneNumbersByFirestation("2");

        assertNotNull(result);
        assertTrue(!result.getPhoneNumbers().isEmpty());
    }

    @Test
    void testGetPhoneNumbersByFirestation_FirestationNotFound() {
        // Cas de test pour une caserne inconnue
        PhoneByFireStation result = phoneAlertService.getPhoneNumbersByFirestation("999");

        assertNotNull(result);
        assertTrue(result.getPhoneNumbers().isEmpty());
    }
}
