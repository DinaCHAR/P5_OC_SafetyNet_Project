package com.openclassroom.api.controller;

import com.openclassroom.api.model.ChildAlert;
import com.openclassroom.api.service.ChildAlertService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChildAlertController.class)
public class ChildAlertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChildAlertService childAlertService;

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ChildAlertControllerTest.class);

    @Test
    void testGetChildrenByAddress() throws Exception {
        String address = "123 Main St";
        
        // Logging pour voir ce qui se passe dans le test
        logger.info("Début du test : testGetChildrenByAddress avec l'adresse {}", address);
        System.out.println("Début du test : testGetChildrenByAddress avec l'adresse " + address);

        // Création d'une réponse simulée
        ChildAlert mockResponse = new ChildAlert();
        mockResponse.setChildren(List.of(Map.of("firstName", "John", "lastName", "Doe", "age", 10)));
        mockResponse.setHouseholdMembers(List.of(Map.of("firstName", "Jane", "lastName", "Doe")));

        // Simuler la réponse du service
        when(childAlertService.getChildrenByAddress(address)).thenReturn(mockResponse);
        
        // Exécution de la requête MockMvc et vérification de la réponse
        mockMvc.perform(get("/childAlert").param("address", address))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.children[0].firstName").value("John"))
                .andExpect(jsonPath("$.householdMembers[0].firstName").value("Jane"));

        // Log et print pour confirmer la fin du test
        logger.info("Fin du test : testGetChildrenByAddress");
        System.out.println("Fin du test : testGetChildrenByAddress");
        
        // Vérification de l'appel au service
        verify(childAlertService, times(1)).getChildrenByAddress(address);
        logger.info("Le service 'getChildrenByAddress' a été appelé une fois avec l'adresse : {}", address);
        System.out.println("Le service 'getChildrenByAddress' a été appelé une fois avec l'adresse : " + address);
    }
}
