package com.openclassroom.SafetyNet;

import com.openclassroom.api.model.MedicalRecord;
import com.openclassroom.api.model.Person;
import com.openclassroom.api.repository.MyRepository;
import com.openclassroom.api.service.PersonInfoLastNameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PersonInfoLastNameTest {

    @Mock
    private MyRepository myRepository;

    @InjectMocks
    private PersonInfoLastNameService personInfoLastNameService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPersonInfoByLastName_Found() {
        // Mock des données
        Person person = new Person("John", "Doe", "123 Main St", "Springfield", null, null, "john.doe@email.com");
        MedicalRecord medicalRecord = new MedicalRecord("John", "Doe", "01/01/1990", Arrays.asList("Aspirin"), Arrays.asList("Peanuts"));

        when(myRepository.getPersons()).thenReturn(Collections.singletonList(person));
        when(myRepository.getMedicalRecords()).thenReturn(Collections.singletonList(medicalRecord));

        System.out.println("Mock Person : " + myRepository.getPersons());
        System.out.println("Mock MedicalRecord : " + myRepository.getMedicalRecords());
        
        // Appel du service
        List<Map<String, Object>> result = personInfoLastNameService.getPersonInfoByLastName("Doe");
        
        System.out.println("Résultat : " + result);

        // Vérification
        assertEquals(1, result.size());
        System.out.println("Vérification : Taille de la réponse = " + result.size());

        assertEquals("John", result.get(0).get("firstName"));
        assertEquals("Doe", result.get(0).get("lastName"));
        assertEquals("123 Main St", result.get(0).get("address"));
        assertEquals("john.doe@email.com", result.get(0).get("email"));
        assertEquals(Collections.singletonList("Aspirin"), result.get(0).get("medications"));
        assertEquals(Collections.singletonList("Peanuts"), result.get(0).get("allergies"));

        System.out.println("Vérification réussie : Les informations de la personne sont correctes.");
    }

    @Test
    public void testGetPersonInfoByLastName_NoMedicalRecord() {
        // Mock des données
        Person person = new Person("Jane", "Doe", "456 Elm St", "Springfield", null, null, "jane.doe@email.com");
        when(myRepository.getPersons()).thenReturn(Collections.singletonList(person));
        when(myRepository.getMedicalRecords()).thenReturn(Collections.emptyList());

        System.out.println("Mock Person : " + myRepository.getPersons());
        System.out.println("Mock MedicalRecord : " + myRepository.getMedicalRecords());
        
        // Appel du service
        List<Map<String, Object>> result = personInfoLastNameService.getPersonInfoByLastName("Doe");

        System.out.println("Résultat : " + result);

        // Vérification
        assertEquals(0, result.size());
        System.out.println("Vérification réussie : Aucun dossier médical trouvé pour cette personne.");
    }

    @Test
    public void testGetPersonInfoByLastName_NotFound() {
        when(myRepository.getPersons()).thenReturn(Collections.emptyList());

        System.out.println("Mock Person : " + myRepository.getPersons());

        // Appel du service
        List<Map<String, Object>> result = personInfoLastNameService.getPersonInfoByLastName("Smith");

        System.out.println("Résultat : " + result);

        // Vérification
        assertEquals(0, result.size());
        System.out.println("Vérification réussie : Aucune personne trouvée avec le nom de famille 'Smith'.");
    }
}
