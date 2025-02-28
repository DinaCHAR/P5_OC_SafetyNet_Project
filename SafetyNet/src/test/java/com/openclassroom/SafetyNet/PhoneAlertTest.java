package com.openclassroom.SafetyNet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.openclassroom.api.model.FireStations;
import com.openclassroom.api.model.Person;
import com.openclassroom.api.model.PhoneByFireStation;
import com.openclassroom.api.repository.MyRepository;
import com.openclassroom.api.service.PhoneAlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PhoneAlertTest {

	    @Mock
	    private MyRepository myRepository;
	
	    @InjectMocks
	    private PhoneAlertService phoneAlertService;
	
	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }
	
	    @Test
	    public void testGetPhoneNumbersByFirestation_Success() {
	        // Mock des données
	        List<FireStations> fireStations = Arrays.asList(
	            new FireStations("1234 Main St", "1"),
	            new FireStations("5678 Elm St", "1")
	        );
	        List<Person> persons = Arrays.asList(
	            new Person("John", "Doe", "1234 Main St", "Culver", "97451", "123-456-7890", "john@example.com"),
	            new Person("Jane", "Doe", "5678 Elm St", "Culver", "97451", "987-654-3210", "jane@example.com"),
	            new Person("Mike", "Smith", "9101 Oak St", "Culver", "97451", "111-222-3333", "mike@example.com")
	        );
	
	        when(myRepository.getFireStations()).thenReturn(fireStations);
	        when(myRepository.getPersons()).thenReturn(persons);
	
	        // Appel du service
	        System.out.println("Appel au service pour récupérer les numéros de téléphone des stations.");
	        PhoneByFireStation result = phoneAlertService.getPhoneNumbersByFirestation("1");
	
	        // Vérifications
	        assertNotNull(result);
	        List<String> phoneNumbers = result.getPhoneNumbers();
	        assertEquals(2, phoneNumbers.size());
	        assertTrue(phoneNumbers.contains("123-456-7890"));
	        assertTrue(phoneNumbers.contains("987-654-3210"));
	
	        System.out.println("Résultat obtenu : " + phoneNumbers);
	    }
	
	    @Test
	    public void testGetPhoneNumbersByFirestation_NoMatch() {
	        System.out.println("Test sans correspondance : aucune station et aucune personne.");
	
	        when(myRepository.getFireStations()).thenReturn(Collections.emptyList());
	        when(myRepository.getPersons()).thenReturn(Collections.emptyList());
	
	        PhoneByFireStation result = phoneAlertService.getPhoneNumbersByFirestation("1");
	
	        assertNotNull(result);
	        assertTrue(result.getPhoneNumbers().isEmpty());
	
	        System.out.println("Aucun numéro trouvé, résultat : " + result.getPhoneNumbers());
	    }
	
	    @Test
	    public void testGetPhoneNumbersByFirestation_NoPersonsAtAddresses() {
	        List<FireStations> fireStations = Collections.singletonList(new FireStations("1234 Main St", "1"));
	        System.out.println("Test avec des stations, mais aucune personne correspondante.");
	
	        when(myRepository.getFireStations()).thenReturn(fireStations);
	        when(myRepository.getPersons()).thenReturn(Collections.emptyList());
	
	        PhoneByFireStation result = phoneAlertService.getPhoneNumbersByFirestation("1");
	
	        assertNotNull(result);
	        assertTrue(result.getPhoneNumbers().isEmpty());
	
	        System.out.println("Aucun numéro de téléphone trouvé pour cette station : " + result.getPhoneNumbers());
	    }
	}
