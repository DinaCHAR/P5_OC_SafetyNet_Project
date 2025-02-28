package com.openclassroom.SafetyNet;

import com.openclassroom.api.controller.MedicalRecordController;
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
public class MedicalRecordTest {

    @Mock
    private MedicalRecordService medicalRecordService;

    @InjectMocks
    private MedicalRecordController medicalRecordController;

    @Test
    public void testAddMedicalRecord() {
        MedicalRecord medicalRecord = new MedicalRecord("John", "Doe", "01/01/1990", null, null);
        medicalRecord.setMedications(Arrays.asList("med1", "med2"));
        medicalRecord.setAllergies(Arrays.asList("allergy1"));

        when(medicalRecordService.addMedicalRecord(medicalRecord)).thenReturn(true);

        System.out.println("Test : Ajouter un dossier médical pour " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName());

        // Appel de l'endpoint
        ResponseEntity<String> response = medicalRecordController.addMedicalRecord(medicalRecord);

        System.out.println("Réponse : " + response);

        // Vérifications
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Medical record successfully added.", response.getBody());

        System.out.println("Vérification réussie : Dossier médical ajouté avec succès.");

        // Vérification de l'appel du service
        verify(medicalRecordService, times(1)).addMedicalRecord(medicalRecord);
        System.out.println("Vérification de l'appel à medicalRecordService.addMedicalRecord() effectuée avec succès.");
    }

    @Test
    public void testUpdateMedicalRecord() {
        MedicalRecord updatedRecord = new MedicalRecord("John", "Doe", "01/01/1990", null, null);
        updatedRecord.setMedications(Arrays.asList("newMed1"));
        updatedRecord.setAllergies(Arrays.asList("newAllergy"));

        when(medicalRecordService.updateMedicalRecord(updatedRecord)).thenReturn(true);

        System.out.println("Test : Mettre à jour le dossier médical pour " + updatedRecord.getFirstName() + " " + updatedRecord.getLastName());

        // Appel de l'endpoint
        ResponseEntity<String> response = medicalRecordController.updateMedicalRecord(updatedRecord);

        System.out.println("Réponse : " + response);

        // Vérifications
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Medical record successfully updated.", response.getBody());

        System.out.println("Vérification réussie : Dossier médical mis à jour avec succès.");

        // Vérification de l'appel du service
        verify(medicalRecordService, times(1)).updateMedicalRecord(updatedRecord);
        System.out.println("Vérification de l'appel à medicalRecordService.updateMedicalRecord() effectuée avec succès.");
    }

    @Test
    public void testDeleteMedicalRecord() {
        String firstName = "John";
        String lastName = "Doe";

        when(medicalRecordService.deleteMedicalRecord(firstName, lastName)).thenReturn(true);

        System.out.println("Test : Supprimer le dossier médical pour " + firstName + " " + lastName);

        // Appel de l'endpoint
        ResponseEntity<String> response = medicalRecordController.deleteMedicalRecord(firstName, lastName);

        System.out.println("Réponse : " + response);

        // Vérifications
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Medical record successfully deleted.", response.getBody());

        System.out.println("Vérification réussie : Dossier médical supprimé avec succès.");

        // Vérification de l'appel du service
        verify(medicalRecordService, times(1)).deleteMedicalRecord(firstName, lastName);
        System.out.println("Vérification de l'appel à medicalRecordService.deleteMedicalRecord() effectuée avec succès.");
    }
}
