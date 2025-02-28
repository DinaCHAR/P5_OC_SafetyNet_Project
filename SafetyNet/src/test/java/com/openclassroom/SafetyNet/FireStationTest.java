package com.openclassroom.SafetyNet;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.openclassroom.api.controller.FireStationController;
import com.openclassroom.api.model.FireStations;
import com.openclassroom.api.model.PersonByFirestation;
import com.openclassroom.api.service.FireStationService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class FireStationTest {

    @Mock
    private FireStationService fireStationService;

    @InjectMocks
    private FireStationController fireStationController;

    @Test
    public void testGetPersonsCoveredByStation() {
        String stationNumber = "1";
        PersonByFirestation mockResponse = new PersonByFirestation();
        mockResponse.setChildCount(2);
        mockResponse.setAdultCount(3);

        System.out.println("Test : Obtenir les personnes couvertes par la station " + stationNumber);
        
        when(fireStationService.getPersonsCoveredByStation(stationNumber)).thenReturn(mockResponse);

        PersonByFirestation response = fireStationController.getPersonsCoveredByStation(stationNumber);

        System.out.println("Response: " + response);
        
        assertNotNull(response);
        assertEquals(2, response.getChildCount());
        assertEquals(3, response.getAdultCount());
        verify(fireStationService, times(1)).getPersonsCoveredByStation(stationNumber);
    }

    @Test
    public void testAddFireStation() {
        String address = "123 Main St";
        int station = 1;
        FireStations mockStation = new FireStations();
        mockStation.setAddress(address);
        mockStation.setStation(String.valueOf(station));

        when(fireStationService.addFireStationMapping(address, station)).thenReturn(mockStation);
        
        System.out.println("Test : Ajouter un mappage de station de pompiers pour l'adresse : " + address + " a la station " + station);

        ResponseEntity<String> response = fireStationController.addFireStation(address, station);

        System.out.println("Response: " + response);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Fire station mapping successfully added.", response.getBody());
        verify(fireStationService, times(1)).addFireStationMapping(address, station);
    }

    @Test
    public void testUpdateFireStation() {
        String address = "123 Main St";
        String newStation = "2";
        FireStations mockStation = new FireStations();
        mockStation.setAddress(address);
        mockStation.setStation(newStation);

        when(fireStationService.updateFireStationMapping(address, newStation)).thenReturn(mockStation);

        
        System.out.println("Test : Mettre à jour le mappage de la station de pompiers pour l'adresse : " + address + " à la nouvelle station " + newStation);

        ResponseEntity<String> response = fireStationController.updateFireStation(address, newStation);
        
        System.out.println("Response: " + response);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Fire station mapping successfully updated.", response.getBody());
        verify(fireStationService, times(1)).updateFireStationMapping(address, newStation);
    }

    @Test
    public void testDeleteFireStation() {
        String address = "123 Main St";

        when(fireStationService.deleteFireStationMapping(address)).thenReturn(true);
        
        System.out.println("Test : Supprimer le mappage de la station de pompiers pour l'adresse : " + address);

        ResponseEntity<String> response = fireStationController.deleteFireStation(address);
        
        System.out.println("Response: " + response);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Fire station mapping successfully deleted.", response.getBody());
        verify(fireStationService, times(1)).deleteFireStationMapping(address);
    }
}
