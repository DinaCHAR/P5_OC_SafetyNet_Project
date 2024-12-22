package com.openclassroom.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassroom.api.model.ChildAlert;
import com.openclassroom.api.model.MedicalRecord;
import com.openclassroom.api.model.Person;
import com.openclassroom.api.repository.MyRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChildAlertService {

    @Autowired
    private MyRepository myRepository;

    public ChildAlert getChildrenByAddress(String address) {
        List<Person> personsAtAddress = new ArrayList<>();
        List<Map<String, Object>> children = new ArrayList<>();
        List<Map<String, Object>> householdMembers = new ArrayList<>();

        // Trouver toutes les personnes vivant à l'adresse
        for (Person person : myRepository.getPersons()) {
            if (person.getAddress().equalsIgnoreCase(address)) { // Comparaison insensible à la casse
                personsAtAddress.add(person);
            }
        }

        // Identifier les enfants et les membres du foyer
        for (Person person : personsAtAddress) {
            MedicalRecord medicalRecord = findMedicalRecordForPerson(person);
            if (medicalRecord != null) {
                int age = medicalRecord.getAge(); // Appel de la méthode dans MedicalRecord
                if (age <= 18) { // Si enfant (<=18 ans)
                    Map<String, Object> childInfo = new HashMap<>();
                    childInfo.put("firstName", person.getFirstName());
                    childInfo.put("lastName", person.getLastName());
                    childInfo.put("age", age);
                    children.add(childInfo);
                } else { // Si adulte
                    Map<String, Object> memberInfo = new HashMap<>();
                    memberInfo.put("firstName", person.getFirstName());
                    memberInfo.put("lastName", person.getLastName());
                    householdMembers.add(memberInfo);
                }
            }
        }

        // Construire la réponse avec le modèle ChildAlert
        ChildAlert childAlert = new ChildAlert();
        childAlert.setPersons(personsAtAddress);
        childAlert.setChildren(children.isEmpty() ? null : children);
        childAlert.setHouseholdMembers(householdMembers.isEmpty() ? null : householdMembers);

        // Retourner la réponse
        return childAlert;
    }

    private MedicalRecord findMedicalRecordForPerson(Person person) {
        return myRepository.getMedicalRecords().stream()
                .filter(record ->
                        record.getFirstName().equalsIgnoreCase(person.getFirstName()) &&
                        record.getLastName().equalsIgnoreCase(person.getLastName()))
                .findFirst()
                .orElse(null);
    }
}