package com.openclassroom.api.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassroom.api.repository.MyRepository;

@SpringBootTest
public class CommunityEmailServiceTest {

    @Autowired
    private CommunityEmailService communityEmailService;

    @Autowired
    private MyRepository myRepository; // Charger les vrais données 

    @BeforeEach
    void setUp() {
    	myRepository.Init();
    }

    @Test
    void testGetEmailsByCity_WithResidents() {
        String testCity = "Culver";
        List<String> result = communityEmailService.getEmailsByCity(testCity);
        
        assertNotNull(result, "La liste des emails ne doit pas être null");
        assertFalse(result.isEmpty(), "Il doit y avoir au moins un email dans la ville");
        
        for (String email : result) {
            assertTrue(email.contains("@"), "L'email doit contenir un @");
        }
    }

    @Test
    void testGetEmailsByCity_NoResidents() {
        String testCity = "Lyon";
        List<String> result = communityEmailService.getEmailsByCity(testCity);
        
        assertNotNull(result, "Le résultat ne doit pas être null");
        assertTrue(result.isEmpty(), "La liste doit être vide car aucun habitant n'y réside");
    }
}