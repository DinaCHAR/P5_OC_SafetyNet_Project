package com.openclassroom.api.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassroom.api.model.ChildAlert;
import com.openclassroom.api.repository.MyRepository;

@SpringBootTest
public class ChildAlertServiceTest {

    @Autowired
    private ChildAlertService childAlertService;

    @Autowired
    private MyRepository myRepository;

    @BeforeEach
    void setUp() {
    	
    	//Reset a chaque fois
    	myRepository.Init(); 
    }

    @Test
    void testGetChildrenByAddress_WithChildren() {
        String testAddress = "1509 Culver St"; // Adresse qui contient des enfants dans la data.json
        ChildAlert result = childAlertService.getChildrenByAddress(testAddress);
        
        assertNotNull(result, "Le résultat ne doit pas être null");
        assertNotNull(result.getChildren(), "La liste des enfants ne doit pas être null");
        assertFalse(result.getChildren().isEmpty(), "Il doit y avoir au moins un enfant");
        
        for (Map<String, Object> child : result.getChildren()) {
            assertTrue((int) child.get("age") <= 18, "L'âge de l'enfant doit être inférieur ou égal à 18");
        }
    }

    @Test
    void testGetChildrenByAddress_NoChildren() {
        String testAddress = "29 15th St"; // Adresse qui contient seulement des adultes
        ChildAlert result = childAlertService.getChildrenByAddress(testAddress);
        
        assertNotNull(result, "Le résultat ne doit pas être null");
        assertNull(result.getChildren(), "Il ne doit pas y avoir d'enfants");
        assertNotNull(result.getHouseholdMembers(), "La liste des adultes ne doit pas être null");
        assertFalse(result.getHouseholdMembers().isEmpty(), "Il doit y avoir au moins un adulte");
    }
}
