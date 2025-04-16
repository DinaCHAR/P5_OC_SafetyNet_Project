package com.openclassroom.api.controller;

import com.openclassroom.api.model.PhoneByFireStation;
import com.openclassroom.api.service.PhoneAlertService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PhoneControllerTest {

    @Mock
    private PhoneAlertService phoneAlertService;

    @InjectMocks
    private PhoneAlertController phoneAlertController;

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PhoneControllerTest.class);

    @Test
    public void testGetPhoneNumbers_Success() {
        // Préparation des données de test
        String firestationNumber = "1";
        PhoneByFireStation phoneByFireStation = new PhoneByFireStation();
        phoneByFireStation.setPhoneNumbers(List.of("123456789", "987654321"));
        
        logger.info("Début du test : testGetPhoneNumbers_Success pour caserne {}", firestationNumber);
        System.out.println("Début du test : testGetPhoneNumbers_Success pour caserne " + firestationNumber);

        // Simulation du service pour renvoyer des numéros de téléphone
        when(phoneAlertService.getPhoneNumbersByFirestation(firestationNumber)).thenReturn(phoneByFireStation);
        
        // Appel du contrôleur
        PhoneByFireStation response = phoneAlertController.getPhoneNumbers(firestationNumber);
        
        // Vérification des résultats
        assertNotNull(response);
        assertEquals(2, response.getPhoneNumbers().size());
        assertTrue(response.getPhoneNumbers().contains("123456789"));
        assertTrue(response.getPhoneNumbers().contains("987654321"));
        
        // Log et System.out.print pour confirmer la fin du test
        logger.info("Fin du test : testGetPhoneNumbers_Success pour caserne {}", firestationNumber);
        System.out.println("Fin du test : testGetPhoneNumbers_Success pour caserne " + firestationNumber);

        // Vérification de l'appel du service
        verify(phoneAlertService, times(1)).getPhoneNumbersByFirestation(firestationNumber);
    }

    @Test
    public void testGetPhoneNumbers_NoNumbersFound() {
        // Préparation des données de test
        String firestationNumber = "2";
        PhoneByFireStation phoneByFireStation = new PhoneByFireStation();
        phoneByFireStation.setPhoneNumbers(List.of());
        
        logger.info("Début du test : testGetPhoneNumbers_NoNumbersFound pour caserne {}", firestationNumber);
        System.out.println("Début du test : testGetPhoneNumbers_NoNumbersFound pour caserne " + firestationNumber);

        // Simulation du service pour renvoyer une liste vide
        when(phoneAlertService.getPhoneNumbersByFirestation(firestationNumber)).thenReturn(phoneByFireStation);
        
        // Appel du contrôleur
        PhoneByFireStation response = phoneAlertController.getPhoneNumbers(firestationNumber);
        
        // Vérification des résultats
        assertNotNull(response);
        assertTrue(response.getPhoneNumbers().isEmpty());
        
        // Log et System.out.print pour confirmer la fin du test
        logger.info("Fin du test : testGetPhoneNumbers_NoNumbersFound pour caserne {}", firestationNumber);
        System.out.println("Fin du test : testGetPhoneNumbers_NoNumbersFound pour caserne " + firestationNumber);

        // Vérification de l'appel du service
        verify(phoneAlertService, times(1)).getPhoneNumbersByFirestation(firestationNumber);
    }

    @Test
    public void testGetPhoneNumbers_EmptyFirestationNumber() {
        // Préparation des données de test
        String firestationNumber = "";
        PhoneByFireStation phoneByFireStation = new PhoneByFireStation();
        phoneByFireStation.setPhoneNumbers(List.of());
        
        logger.info("Début du test : testGetPhoneNumbers_EmptyFirestationNumber pour caserne {}", firestationNumber);
        System.out.println("Début du test : testGetPhoneNumbers_EmptyFirestationNumber pour caserne " + firestationNumber);

        // Simulation du service pour renvoyer une liste vide pour un numéro de caserne vide
        when(phoneAlertService.getPhoneNumbersByFirestation(firestationNumber)).thenReturn(phoneByFireStation);
        
        // Appel du contrôleur
        PhoneByFireStation response = phoneAlertController.getPhoneNumbers(firestationNumber);
        
        // Vérification des résultats
        assertNotNull(response);
        assertTrue(response.getPhoneNumbers().isEmpty());
        
        // Log et System.out.print pour confirmer la fin du test
        logger.info("Fin du test : testGetPhoneNumbers_EmptyFirestationNumber pour caserne {}", firestationNumber);
        System.out.println("Fin du test : testGetPhoneNumbers_EmptyFirestationNumber pour caserne " + firestationNumber);

        // Vérification de l'appel du service
        verify(phoneAlertService, times(1)).getPhoneNumbersByFirestation(firestationNumber);
    }
}
