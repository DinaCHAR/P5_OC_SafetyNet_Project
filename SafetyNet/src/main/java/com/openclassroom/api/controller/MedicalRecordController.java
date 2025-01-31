package com.openclassroom.api.controller;

import com.openclassroom.api.model.MedicalRecord;
import com.openclassroom.api.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    // Endpoint pour ajouter un dossier médical
    @PostMapping
    public ResponseEntity<String> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        boolean added = medicalRecordService.addMedicalRecord(medicalRecord);
        if (added) {
            return ResponseEntity.ok("Medical record successfully added.");
        } else {
            return ResponseEntity.badRequest().body("Medical record already exists.");
        }
    }

    // Endpoint pour mettre à jour un dossier médical
    @PutMapping
    public ResponseEntity<String> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        boolean updated = medicalRecordService.updateMedicalRecord(medicalRecord);
        if (updated) {
            return ResponseEntity.ok("Medical record successfully updated.");
        } else {
            return ResponseEntity.badRequest().body("Medical record not found.");
        }
    }

    // Endpoint pour supprimer un dossier médical
    @DeleteMapping
    public ResponseEntity<String> deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
        boolean deleted = medicalRecordService.deleteMedicalRecord(firstName, lastName);
        if (deleted) {
            return ResponseEntity.ok("Medical record successfully deleted.");
        } else {
            return ResponseEntity.badRequest().body("Medical record not found.");
        }
    }
}
