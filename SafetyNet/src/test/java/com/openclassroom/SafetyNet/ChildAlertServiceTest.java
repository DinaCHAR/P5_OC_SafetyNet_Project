package com.openclassroom.SafetyNet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.openclassroom.api.model.ChildAlert;
import com.openclassroom.api.model.MedicalRecord;
import com.openclassroom.api.model.Person;
import com.openclassroom.api.repository.MyRepository;
import com.openclassroom.api.service.ChildAlertService;
	
	@SpringBootTest
	public class ChildAlertServiceTest {
	    
	    @MockBean
	    private MyRepository myRepository;
	    
	    @Autowired
	    private ChildAlertService childAlertService;
	    
	    private List<Person> mockPersons;
	    private List<MedicalRecord> mockMedicalRecords;
	
	    @BeforeEach
	    void setUp() {
	        // Initialisation des données mockées
	        mockPersons = new ArrayList<>();
	        mockPersons.add(new Person("Julie", "Dupont", "123 rte du Mans"));
	        mockPersons.add(new Person("Robert", "Allard", "56 route national"));
	
	        mockMedicalRecords = new ArrayList<>();
	        mockMedicalRecords.add(new MedicalRecord("Julie", "Dupont", "05/13/2011", null, null));
	        mockMedicalRecords.add(new MedicalRecord("Robert", "Allard", "09/12/1991", null, null));
	
	        // Simuler le comportement du repository
	        when(myRepository.getPersons()).thenReturn(mockPersons);
	        when(myRepository.getMedicalRecords()).thenReturn(mockMedicalRecords);
	    }
	
	    @Test
	    public void testChildAlertService() {
	        // Appel de la méthode à tester
	        ChildAlert result = childAlertService.getChildrenByAddress("123 rte du Mans");
	
	        // Vérification que le résultat n'est pas null
	        assertNotNull(result, "L'objet ChildAlert ne doit pas être null");
	
	        // Vérification que la liste des enfants n'est pas null
	        assertNotNull(result.getChildren(), "La liste des enfants ne doit pas être null");
	        assertFalse(result.getChildren().isEmpty(), "La liste des enfants ne doit pas être vide");
	
	        // Vérification du nombre d'enfants trouvés
	        assertEquals(1, result.getChildren().size(), "Il doit y avoir un enfant dans la liste");
	
	        // Vérification des détails de l'enfant
	        Map<String, Object> child = result.getChildren().get(0);
	        assertEquals("Julie", child.get("firstName"));
	        assertEquals("Dupont", child.get("lastName"));
	        assertEquals(13, (int) child.get("age")); // Cast pour éviter une erreur de type
	
	        System.out.println("Test OK - Nombre d'enfants trouvés : " + result.getChildren().size());
	    }
	}
