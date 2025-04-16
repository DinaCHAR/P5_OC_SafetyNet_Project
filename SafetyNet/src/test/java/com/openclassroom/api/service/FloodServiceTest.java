package com.openclassroom.api.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassroom.api.repository.MyRepository;

@SpringBootTest
public class FloodServiceTest {

    @Autowired
    private FloodService floodService;
    
    @Autowired
    private MyRepository myRepository;

    @BeforeEach
    void setUp() {
    	
    	myRepository.Init();

    }

    @Test
    void testGetHouseholdsByStations_validStations() {
        List<Integer> stationNumbers = Arrays.asList(1, 2);
        Map<String, List<Map<String, Object>>> result = floodService.getHouseholdsByStations(stationNumbers);
        
        assertNotNull(result);
        assertFalse(result.isEmpty());
        
        for (Map.Entry<String, List<Map<String, Object>>> entry : result.entrySet()) {
            assertNotNull(entry.getKey()); // Adresse ne doit pas être null
            assertNotNull(entry.getValue()); // Liste des résidents ne doit pas être null
            assertFalse(entry.getValue().isEmpty()); // Il doit y avoir au moins un résident
        }
    }

    @Test
    void testGetHouseholdsByStations_invalidStations() {
        List<Integer> stationNumbers = Arrays.asList(999); // Caserne inexistante
        Map<String, List<Map<String, Object>>> result = floodService.getHouseholdsByStations(stationNumbers);
        
        assertNotNull(result);
        assertTrue(result.isEmpty()); // Aucune adresse ne doit être retournée
    }
}
