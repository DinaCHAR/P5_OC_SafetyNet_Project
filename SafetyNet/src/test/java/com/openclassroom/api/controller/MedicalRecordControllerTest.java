package com.openclassroom.api.controller;

import com.openclassroom.api.model.MedicalRecord;
import com.openclassroom.api.service.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordControllerTest {

    @Mock
    private MedicalRecordService medicalRecordService;

    @InjectMocks
    private MedicalRecordController medicalRecordController;

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MedicalRecordControllerTest.class);

    @Test
    public void testAddMedicalRecord_Success() {
        // Création d'un dossier médical
        MedicalRecord medicalRecord = new MedicalRecord("John", "Doe", "01/01/1990", null, null);
        medicalRecord.setMedications(Arrays.asList("med1", "med2"));
        medicalRecord.setAllergies(Arrays.asList("allergy1"));

        // Log et System.out.print pour suivre l'exécution
        logger.info("Début du test : testAddMedicalRecord_Success pour {}", medicalRecord);
        System.out.println("Début du test : testAddMedicalRecord_Success pour " + medicalRecord);

        // Simuler l'ajout réussi d'un dossier médical
        when(medicalRecordService.addMedicalRecord(medicalRecord)).thenReturn(true);

        // Appel de l'endpoint
        ResponseEntity<String> response = medicalRecordController.addMedicalRecord(medicalRecord);

        // Vérification de la réponse
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Medical record successfully added.", response.getBody());

        // Log et System.out.print pour confirmer la fin du test
        logger.info("Fin du test : testAddMedicalRecord_Success pour {}", medicalRecord);
        System.out.println("Fin du test : testAddMedicalRecord_Success pour " + medicalRecord);

        // Vérification du service
        verify(medicalRecordService, times(1)).addMedicalRecord(medicalRecord);
    }

    @Test
    public void testAddMedicalRecord_Failure() {
        // Création d'un dossier médical
        MedicalRecord medicalRecord = new MedicalRecord("John", "Doe", "01/01/1990", null, null);
        medicalRecord.setMedications(Arrays.asList("med1", "med2"));
        medicalRecord.setAllergies(Arrays.asList("allergy1"));

        // Log et System.out.print pour suivre l'exécution
        logger.info("Début du test : testAddMedicalRecord_Failure pour {}", medicalRecord);
        System.out.println("Début du test : testAddMedicalRecord_Failure pour " + medicalRecord);

        // Simuler un échec de l'ajout (dossier déjà existant)
        when(medicalRecordService.addMedicalRecord(medicalRecord)).thenReturn(false);

        // Appel de l'endpoint
        ResponseEntity<String> response = medicalRecordController.addMedicalRecord(medicalRecord);

        // Vérification de la réponse
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Medical record already exists.", response.getBody());

        // Log et System.out.print pour confirmer la fin du test
        logger.info("Fin du test : testAddMedicalRecord_Failure pour {}", medicalRecord);
        System.out.println("Fin du test : testAddMedicalRecord_Failure pour " + medicalRecord);

        // Vérification du service
        verify(medicalRecordService, times(1)).addMedicalRecord(medicalRecord);
    }

    @Test
    public void testUpdateMedicalRecord_Success() {
        // Création d'un dossier médical mis à jour
        MedicalRecord updatedRecord = new MedicalRecord("John", "Doe", "01/01/1990", null, null);
        updatedRecord.setMedications(Arrays.asList("newMed1"));
        updatedRecord.setAllergies(Arrays.asList("newAllergy"));

        // Log et System.out.print pour suivre l'exécution
        logger.info("Début du test : testUpdateMedicalRecord_Success pour {}", updatedRecord);
        System.out.println("Début du test : testUpdateMedicalRecord_Success pour " + updatedRecord);

        // Simuler une mise à jour réussie
        when(medicalRecordService.updateMedicalRecord(updatedRecord)).thenReturn(true);

        // Appel de l'endpoint
        ResponseEntity<String> response = medicalRecordController.updateMedicalRecord(updatedRecord);

        // Vérification de la réponse
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Medical record successfully updated.", response.getBody());

        // Log et System.out.print pour confirmer la fin du test
        logger.info("Fin du test : testUpdateMedicalRecord_Success pour {}", updatedRecord);
        System.out.println("Fin du test : testUpdateMedicalRecord_Success pour " + updatedRecord);

        // Vérification du service
        verify(medicalRecordService, times(1)).updateMedicalRecord(updatedRecord);
    }

    @Test
    public void testUpdateMedicalRecord_Failure() {
        // Création d'un dossier médical mis à jour
        MedicalRecord updatedRecord = new MedicalRecord("John", "Doe", "01/01/1990", null, null);
        updatedRecord.setMedications(Arrays.asList("newMed1"));
        updatedRecord.setAllergies(Arrays.asList("newAllergy"));

        // Log et System.out.print pour suivre l'exécution
        logger.info("Début du test : testUpdateMedicalRecord_Failure pour {}", updatedRecord);
        System.out.println("Début du test : testUpdateMedicalRecord_Failure pour " + updatedRecord);

        // Simuler une échec de mise à jour (dossier non trouvé)
        when(medicalRecordService.updateMedicalRecord(updatedRecord)).thenReturn(false);

        // Appel de l'endpoint
        ResponseEntity<String> response = medicalRecordController.updateMedicalRecord(updatedRecord);

        // Vérification de la réponse
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Medical record not found.", response.getBody());

        // Log et System.out.print pour confirmer la fin du test
        logger.info("Fin du test : testUpdateMedicalRecord_Failure pour {}", updatedRecord);
        System.out.println("Fin du test : testUpdateMedicalRecord_Failure pour " + updatedRecord);

        // Vérification du service
        verify(medicalRecordService, times(1)).updateMedicalRecord(updatedRecord);
    }

    @Test
    public void testDeleteMedicalRecord_Success() {
        String firstName = "John";
        String lastName = "Doe";

        // Log et System.out.print pour suivre l'exécution
        logger.info("Début du test : testDeleteMedicalRecord_Success pour {} {}", firstName, lastName);
        System.out.println("Début du test : testDeleteMedicalRecord_Success pour " + firstName + " " + lastName);

        // Simuler une suppression réussie
        when(medicalRecordService.deleteMedicalRecord(firstName, lastName)).thenReturn(true);

        // Appel de l'endpoint
        ResponseEntity<String> response = medicalRecordController.deleteMedicalRecord(firstName, lastName);

        // Vérification de la réponse
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Medical record successfully deleted.", response.getBody());

        // Log et System.out.print pour confirmer la fin du test
        logger.info("Fin du test : testDeleteMedicalRecord_Success pour {} {}", firstName, lastName);
        System.out.println("Fin du test : testDeleteMedicalRecord_Success pour " + firstName + " " + lastName);

        // Vérification du service
        verify(medicalRecordService, times(1)).deleteMedicalRecord(firstName, lastName);
    }

    @Test
    public void testDeleteMedicalRecord_Failure() {
        String firstName = "John";
        String lastName = "Doe";

        // Log et System.out.print pour suivre l'exécution
        logger.info("Début du test : testDeleteMedicalRecord_Failure pour {} {}", firstName, lastName);
        System.out.println("Début du test : testDeleteMedicalRecord_Failure pour " + firstName + " " + lastName);

        // Simuler un échec de suppression (dossier non trouvé)
        when(medicalRecordService.deleteMedicalRecord(firstName, lastName)).thenReturn(false);

        // Appel de l'endpoint
        ResponseEntity<String> response = medicalRecordController.deleteMedicalRecord(firstName, lastName);

        // Vérification de la réponse
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Medical record not found.", response.getBody());

        // Log et System.out.print pour confirmer la fin du test
        logger.info("Fin du test : testDeleteMedicalRecord_Failure pour {} {}", firstName, lastName);
        System.out.println("Fin du test : testDeleteMedicalRecord_Failure pour " + firstName + " " + lastName);

        // Vérification du service
        verify(medicalRecordService, times(1)).deleteMedicalRecord(firstName, lastName);
    }
}
