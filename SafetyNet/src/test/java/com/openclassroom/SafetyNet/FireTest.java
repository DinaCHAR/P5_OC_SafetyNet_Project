package com.openclassroom.SafetyNet;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.openclassroom.api.controller.FireController;
import com.openclassroom.api.model.PersonByFirestation;
import com.openclassroom.api.service.FireService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FireTest {

    @Mock
    private FireService fireService;

    @InjectMocks
    private FireController fireController;

    @Test
    public void testGetPersonsByAddress() {
        // Préparation des données mockées
        String address = "123 Main St";
        PersonByFirestation mockResponse = new PersonByFirestation();
        mockResponse.setChildCount(2);
        mockResponse.setAdultCount(3);
        mockResponse.setPersonsAtAddress(1);  // Numéro de station

        when(fireService.getPersonsByAddress(address)).thenReturn(mockResponse);
        
        System.out.println("Test : Obtention des personnes à l'adresse " + address);


        // Appel de l'endpoint
        PersonByFirestation response = fireController.getPersonsByAddress(address);
        
        System.out.println("Response : " + response);


        // Vérifications
        assertNotNull(response);
        assertEquals(2, response.getChildCount());
        assertEquals(3, response.getAdultCount());
        assertEquals(1, response.getPersonsAtAddress());
        
        System.out.println("Vérifications réussies : nombre d'enfants = " + response.getChildCount() +
                ", nombre d'adultes = " + response.getAdultCount() +
                ", nombre de personnes à l'adresse = " + response.getPersonsAtAddress());

        // Vérification de l'appel du service
        verify(fireService, times(1)).getPersonsByAddress(address);
        System.out.println("Vérification de l'appel à fireService.getPersonsByAddress() effectuée avec succès.");

    }
}

