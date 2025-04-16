package com.openclassroom.api.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassroom.api.model.PersonByFirestation;
import com.openclassroom.api.repository.MyRepository;

@SpringBootTest
public class FireServiceTest {

    @Autowired
    private FireService fireService;
    @Autowired
    private MyRepository myRepository;

    private String testAddress;

    @BeforeEach
    void setUp() {
    	
    	myRepository.Init();
    }

    @Test
    void testGetPersonsByAddress_ShouldReturnResidentsAndFirestation() {
    	String ExistentAddress = "1509 Culver St";
        PersonByFirestation result = fireService.getPersonsByAddress(ExistentAddress);
        
        assertNotNull(result);
        assertNotNull(result.getPersons());
        assertTrue(result.getPersons().size() > 0, "Il devrait y avoir au moins une personne à cette adresse");
        assertTrue(result.getChildCount() >= 0);
        assertTrue(result.getAdultCount() >= 0);
        assertNotNull(result.getPersonsAtAddress(), "Le numéro de caserne devrait être présent");
    }

    @Test
    void testGetPersonsByAddress_ShouldReturnEmptyForNonexistentAddress() {
        String nonExistentAddress = "24 rue libre";
        PersonByFirestation result = fireService.getPersonsByAddress(nonExistentAddress);

        assertNotNull(result);
        assertTrue(result.getPersons().isEmpty(), "Aucune personne ne devrait être trouvée");
        assertEquals(0, result.getChildCount());
        assertEquals(0, result.getAdultCount());
        assertEquals(0, result.getPersonsAtAddress(), "Aucune caserne ne devrait être associée");
    }
}