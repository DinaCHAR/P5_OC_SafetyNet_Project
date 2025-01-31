package com.openclassroom.api.service;

import com.openclassroom.api.model.MedicalRecord;
import com.openclassroom.api.repository.MyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {

    @Autowired
    private MyRepository myRepository;

    // Ajouter un dossier médical
    public boolean addMedicalRecord(MedicalRecord medicalRecord) {
        List<MedicalRecord> medicalRecords = myRepository.getMedicalRecords();
        // Vérifie si un dossier existe déjà pour cette personne
        boolean exists = medicalRecords.stream()
            .anyMatch(record -> record.getFirstName().equals(medicalRecord.getFirstName())
                    && record.getLastName().equals(medicalRecord.getLastName()));
        if (!exists) {
            medicalRecords.add(medicalRecord); // Ajoute le dossier
            return true;
        }
        return false; // Le dossier existe déjà
    }

    // Mettre à jour un dossier médical
    public boolean updateMedicalRecord(MedicalRecord updatedRecord) {
        List<MedicalRecord> medicalRecords = myRepository.getMedicalRecords();
        for (int i = 0; i < medicalRecords.size(); i++) {
            MedicalRecord existingRecord = medicalRecords.get(i);
            // Trouve le dossier correspondant
            if (existingRecord.getFirstName().equals(updatedRecord.getFirstName())
                && existingRecord.getLastName().equals(updatedRecord.getLastName())) {
                // Remplace l'ancien dossier par le nouveau
                medicalRecords.set(i, updatedRecord);
                return true;
            }
        }
        return false; // Le dossier n'existe pas
    }

    // Supprimer un dossier médical
    public boolean deleteMedicalRecord(String firstName, String lastName) {
        List<MedicalRecord> medicalRecords = myRepository.getMedicalRecords();
        // Recherche et supprime le dossier correspondant
        return medicalRecords.removeIf(record -> record.getFirstName().equals(firstName)
                && record.getLastName().equals(lastName));
    }
}
