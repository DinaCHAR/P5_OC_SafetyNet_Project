package com.openclassroom.api.service;

import com.openclassroom.api.model.MedicalRecord;
import com.openclassroom.api.repository.MyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MedicalRecordServiceTest {

    @Autowired
    private MyRepository myRepository;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @BeforeEach
    void setUp() {

        myRepository.Init();
        }

    @Test
    void testAddMedicalRecord_Success() {
        MedicalRecord newRecord = new MedicalRecord("Grue", "Dev", "03/13/1964", List.of("MedAdd"), List.of("AllergyAdd"));

        int sizeBefore = myRepository.getMedicalRecords().size();//Avant l'ajout
        boolean result = medicalRecordService.addMedicalRecord(newRecord);
        int sizeAfter = myRepository.getMedicalRecords().size();//Après l'ajout
        assertTrue(result);
        assertEquals(sizeBefore + 1, sizeAfter, "Le dossier médical n'a pas été ajouté !");
    }

    @Test
    void testAddMedicalRecord_AlreadyExists() {
        MedicalRecord duplicateRecord = new MedicalRecord("John", "Boyd", "03/06/1984", List.of("MedUpdated"), List.of("AllergyUpdated"));
        boolean result = medicalRecordService.addMedicalRecord(duplicateRecord);
        assertFalse(result);
    }

    @Test
    void testUpdateMedicalRecord_Success() {
        MedicalRecord updatedRecord = new MedicalRecord("John", "Boyd", "03/06/1984", List.of("MedUpdated"), List.of("AllergyUpdated"));
        boolean result = medicalRecordService.updateMedicalRecord(updatedRecord);
        assertTrue(result);
        assertEquals("MedUpdated", myRepository.getMedicalRecords().get(0).getMedications().get(0));
    }

    @Test
    void testUpdateMedicalRecord_NotFound() {
        MedicalRecord updatedRecord = new MedicalRecord("Rob", "Boyd", "03/06/1989", List.of("Med2"), List.of("Allergy2"));
        boolean result = medicalRecordService.updateMedicalRecord(updatedRecord);
        assertFalse(result);
    }

    @Test
    void testDeleteMedicalRecord_Success() {
        boolean result = medicalRecordService.deleteMedicalRecord("Jamie", "Peters");
        assertTrue(result);
        //Verifier si juste Jamie a ete supprimer
        assertFalse(myRepository.getMedicalRecords().stream()
        	    .anyMatch(record -> record.getFirstName().equals("Jamie") && record.getLastName().equals("Peters")));    
        }

    @Test
    void testDeleteMedicalRecord_NotFound() {
        boolean result = medicalRecordService.deleteMedicalRecord("Dina", "Boyd");
        assertFalse(result);
    }
}