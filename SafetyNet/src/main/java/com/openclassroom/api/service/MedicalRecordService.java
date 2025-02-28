package com.openclassroom.api.service;

import com.openclassroom.api.model.MedicalRecord;
import com.openclassroom.api.repository.MyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {

    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordService.class);

    @Autowired
    private MyRepository myRepository;

    // Ajouter un dossier médical
    public boolean addMedicalRecord(MedicalRecord medicalRecord) {
        logger.info("Tentative d'ajout du dossier médical pour : {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());

        List<MedicalRecord> medicalRecords = myRepository.getMedicalRecords();
        // Vérifie si un dossier existe déjà pour cette personne
        boolean exists = medicalRecords.stream()
            .anyMatch(record -> record.getFirstName().equals(medicalRecord.getFirstName())
                    && record.getLastName().equals(medicalRecord.getLastName()));

        if (!exists) {
            medicalRecords.add(medicalRecord); // Ajoute le dossier
            logger.info("Dossier médical ajouté pour : {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
            return true;
        }
        
        logger.warn("Le dossier médical pour : {} {} existe déjà.", medicalRecord.getFirstName(), medicalRecord.getLastName());
        return false; // Le dossier existe déjà
    }

    // Mettre à jour un dossier médical
    public boolean updateMedicalRecord(MedicalRecord updatedRecord) {
        logger.info("Tentative de mise à jour du dossier médical pour : {} {}", updatedRecord.getFirstName(), updatedRecord.getLastName());

        List<MedicalRecord> medicalRecords = myRepository.getMedicalRecords();
        for (int i = 0; i < medicalRecords.size(); i++) {
            MedicalRecord existingRecord = medicalRecords.get(i);
            // Trouve le dossier correspondant
            if (existingRecord.getFirstName().equals(updatedRecord.getFirstName())
                && existingRecord.getLastName().equals(updatedRecord.getLastName())) {
                // Remplace l'ancien dossier par le nouveau
                medicalRecords.set(i, updatedRecord);
                logger.info("Dossier médical mis à jour pour : {} {}", updatedRecord.getFirstName(), updatedRecord.getLastName());
                return true;
            }
        }
        
        logger.warn("Aucun dossier médical trouvé pour mettre à jour pour : {} {}", updatedRecord.getFirstName(), updatedRecord.getLastName());
        return false; // Le dossier n'existe pas
    }

    // Supprimer un dossier médical
    public boolean deleteMedicalRecord(String firstName, String lastName) {
        logger.info("Tentative de suppression du dossier médical pour : {} {}", firstName, lastName);

        List<MedicalRecord> medicalRecords = myRepository.getMedicalRecords();
        // Recherche et supprime le dossier correspondant
        boolean removed = medicalRecords.removeIf(record -> record.getFirstName().equals(firstName)
                && record.getLastName().equals(lastName));

        if (removed) {
            logger.info("Dossier médical supprimé pour : {} {}", firstName, lastName);
        } else {
            logger.warn("Aucun dossier médical trouvé à supprimer pour : {} {}", firstName, lastName);
        }

        return removed;
    }
}
