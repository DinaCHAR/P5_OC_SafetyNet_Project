package com.openclassroom.SafetyNet;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.openclassroom.api.controller.FloodController;
import com.openclassroom.api.service.FloodService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class FloodTest {

    @Mock
    private FloodService floodService;

    @InjectMocks
    private FloodController floodController;

    @Test
    public void testGetHouseholdsByStations() {
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

        when(floodService.getHouseholdsByStations(stationNumbers)).thenReturn(mockResponse);
        
        System.out.println("Test : Obtention des foyers pour les stations " + stationNumbers);

        Map<String, List<Map<String, Object>>> response = floodController.getHouseholdsByStations("1,2");
        
        System.out.println("Réponse : " + response);

        assertNotNull(response);
        assertTrue(response.containsKey("123 Main St"));
        assertEquals(1, response.get("123 Main St").size());
        assertEquals("John", response.get("123 Main St").get(0).get("firstName"));
        
        System.out.println("Vérifications réussies : La clé '123 Main St' est présente, le nombre de résidents = " + response.get("123 Main St").size() +
                ", le prénom du premier résident = " + response.get("123 Main St").get(0).get("firstName"));
        //Verification de l'appel du service
        verify(floodService, times(1)).getHouseholdsByStations(stationNumbers);
        System.out.println("Vérification de l'appel à floodService.getHouseholdsByStations() effectuée avec succès.");
    }
}
