package com.openclassroom.api.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.openclassroom.api.service.FloodService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class FloodControllerTest {

    @Mock
    private FloodService floodService;

    @InjectMocks
    private FloodController floodController;

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FloodControllerTest.class);

    @Test
    public void testGetHouseholdsByStations() {
        // Données simulées
        List<Integer> stationNumbers = Arrays.asList(1, 2);
        Map<String, List<Map<String, Object>>> mockResponse = new HashMap<>();

        Map<String, Object> resident1 = new HashMap<>();
        resident1.put("firstName", "John");
        resident1.put("lastName", "Doe");
        resident1.put("phone", "123-456-7890");
        resident1.put("age", 35);
        resident1.put("medications", Arrays.asList("Aspirin"));
        resident1.put("allergies", Arrays.asList("Peanuts"));

        mockResponse.put("123 Main St", Collections.singletonList(resident1));

        // Log et System.out.print pour suivre l'exécution du test
        logger.info("Début du test : testGetHouseholdsByStations avec les numéros de station {}", stationNumbers);
        System.out.println("Début du test : testGetHouseholdsByStations avec les numéros de station " + stationNumbers);

        when(floodService.getHouseholdsByStations(stationNumbers)).thenReturn(mockResponse);

        // Appel de l'endpoint
        Map<String, List<Map<String, Object>>> response = floodController.getHouseholdsByStations("1,2");

        // Vérifications
        assertNotNull(response);
        assertTrue(response.containsKey("123 Main St"));
        assertEquals(1, response.get("123 Main St").size());
        assertEquals("John", response.get("123 Main St").get(0).get("firstName"));

        // Log et System.out.print pour confirmer la fin du test
        logger.info("Fin du test : testGetHouseholdsByStations pour les numéros de station {}", stationNumbers);
        System.out.println("Fin du test : testGetHouseholdsByStations pour les numéros de station " + stationNumbers);

        verify(floodService, times(1)).getHouseholdsByStations(stationNumbers);
    }
}
