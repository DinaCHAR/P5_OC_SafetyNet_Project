package com.openclassroom.api.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.openclassroom.api.model.Person;
import com.openclassroom.api.model.PersonByFirestation;
import com.openclassroom.api.service.FireService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(FireController.class)
class FireControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireService fireService;

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FireControllerTest.class);

    @Test
    void testGetPersonsByAddress() throws Exception {
        String address = "123 Main St";

        // Création des données mockées
        Person person1 = new Person("John", "Doe", address, "123-456-7890", "john@example.com");
        Person person2 = new Person("Jane", "Doe", address, "987-654-3210", "jane@example.com");

        PersonByFirestation mockResponse = new PersonByFirestation();
        mockResponse.setPersons(List.of(person1, person2));
        mockResponse.setChildCount(1);
        mockResponse.setAdultCount(1);
        mockResponse.setPersonsAtAddress(2); // Caserne de pompiers numéro 2

        // Log et System.out.print pour suivre l'exécution du test
        logger.info("Début du test : testGetPersonsByAddress pour l'adresse {}", address);
        System.out.println("Début du test : testGetPersonsByAddress pour l'adresse " + address);

        // Simulation du comportement du service
        when(fireService.getPersonsByAddress(address)).thenReturn(mockResponse);

        // Exécution de la requête GET et validation du résultat
        mockMvc.perform(get("/fire")
                .param("address", address)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.persons[0].firstName").value("John"))
                .andExpect(jsonPath("$.persons[1].firstName").value("Jane"))
                .andExpect(jsonPath("$.childCount").value(1))
                .andExpect(jsonPath("$.adultCount").value(1))
                .andExpect(jsonPath("$.personsAtAddress").value(2)); // Vérification de la caserne de pompiers

        // Log et print pour confirmer la fin du test
        logger.info("Fin du test : testGetPersonsByAddress pour l'adresse {}", address);
        System.out.println("Fin du test : testGetPersonsByAddress pour l'adresse " + address);

        // Vérification de l'appel au service
        verify(fireService, times(1)).getPersonsByAddress(address);
        logger.info("Le service 'getPersonsByAddress' a été appelé une fois avec l'adresse : {}", address);
        System.out.println("Le service 'getPersonsByAddress' a été appelé une fois avec l'adresse : " + address);
    }
}
