package com.openclassroom.api.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import com.openclassroom.api.service.CommunityEmailService;

import java.util.List;

@WebMvcTest(CommunityEmailController.class)
class CommunityEmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean  // Remplace @Mock
    private CommunityEmailService communityEmailService;

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CommunityEmailControllerTest.class);

    @Test
    void testGetCommunityEmail() throws Exception {
        String city = "Culver";
        List<String> mockEmails = List.of("john.doe@example.com", "jane.doe@example.com");

        // Log et System.out.print pour suivre l'exécution du test
        logger.info("Début du test : testGetCommunityEmail pour la ville {}", city);
        System.out.println("Début du test : testGetCommunityEmail pour la ville " + city);

        // Simuler la réponse du service
        when(communityEmailService.getEmailsByCity(city)).thenReturn(mockEmails);

        // Exécution de la requête MockMvc et vérification de la réponse
        ResultActions result = mockMvc.perform(get("/communityEmail")
                .param("city", city)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("john.doe@example.com"))
                .andExpect(jsonPath("$[1]").value("jane.doe@example.com"));

        // Log et print pour confirmer la fin du test
        logger.info("Fin du test : testGetCommunityEmail pour la ville {}", city);
        System.out.println("Fin du test : testGetCommunityEmail pour la ville " + city);

        // Vérification de l'appel au service
        verify(communityEmailService, times(1)).getEmailsByCity(city);
        logger.info("Le service 'getEmailsByCity' a été appelé une fois avec la ville : {}", city);
        System.out.println("Le service 'getEmailsByCity' a été appelé une fois avec la ville : " + city);
    }
}
