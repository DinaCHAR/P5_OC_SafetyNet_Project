package com.openclassroom.api.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.openclassroom.api.model.FireStation;
import com.openclassroom.api.model.PersonByFirestation;
import com.openclassroom.api.service.FireStationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(FireStationController.class)
class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService fireStationService;

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FireStationControllerTest.class);

    @Test
    void testGetPersonsCoveredByStation() throws Exception {
        String stationNumber = "1";
        PersonByFirestation mockResponse = new PersonByFirestation();
        mockResponse.setAdultCount(2);
        mockResponse.setChildCount(1);
        
        // Log et System.out.print pour suivre l'exécution du test
        logger.info("Début du test : testGetPersonsCoveredByStation pour le numéro de station {}", stationNumber);
        System.out.println("Début du test : testGetPersonsCoveredByStation pour le numéro de station " + stationNumber);

        when(fireStationService.getPersonsCoveredByStation(stationNumber)).thenReturn(mockResponse);
        
        mockMvc.perform(get("/firestation").param("stationNumber", stationNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adultCount").value(2))
                .andExpect(jsonPath("$.childCount").value(1));

        // Log et print pour confirmer la fin du test
        logger.info("Fin du test : testGetPersonsCoveredByStation pour le numéro de station {}", stationNumber);
        System.out.println("Fin du test : testGetPersonsCoveredByStation pour le numéro de station " + stationNumber);
    }

    @Test
    void testAddFireStation() throws Exception {
        String address = "123 Main St";
        String station = "1";
        when(fireStationService.addFireStationMapping(address, Integer.parseInt(station)))
                .thenReturn(new FireStation(address, station));

        // Log et System.out.print pour suivre l'exécution du test
        logger.info("Début du test : testAddFireStation pour l'adresse {}", address);
        System.out.println("Début du test : testAddFireStation pour l'adresse " + address);

        mockMvc.perform(post("/firestation")
                .param("address", address)
                .param("station", station)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Fire station mapping successfully added."));

        // Log et print pour confirmer la fin du test
        logger.info("Fin du test : testAddFireStation pour l'adresse {}", address);
        System.out.println("Fin du test : testAddFireStation pour l'adresse " + address);
    }

    @Test
    void testUpdateFireStation() throws Exception {
        String address = "123 Main St";
        String newStation = "2";
        when(fireStationService.updateFireStationMapping(address, newStation))
                .thenReturn(new FireStation(address, newStation));

        // Log et System.out.print pour suivre l'exécution du test
        logger.info("Début du test : testUpdateFireStation pour l'adresse {} et la nouvelle station {}", address, newStation);
        System.out.println("Début du test : testUpdateFireStation pour l'adresse " + address + " et la nouvelle station " + newStation);

        mockMvc.perform(put("/firestation")
                .param("address", address)
                .param("newStation", newStation)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Fire station mapping successfully updated."));

        // Log et print pour confirmer la fin du test
        logger.info("Fin du test : testUpdateFireStation pour l'adresse {} et la nouvelle station {}", address, newStation);
        System.out.println("Fin du test : testUpdateFireStation pour l'adresse " + address + " et la nouvelle station " + newStation);
    }

    @Test
    void testDeleteFireStation() throws Exception {
        String address = "123 Main St";
        when(fireStationService.deleteFireStationMapping(address)).thenReturn(true);

        // Log et System.out.print pour suivre l'exécution du test
        logger.info("Début du test : testDeleteFireStation pour l'adresse {}", address);
        System.out.println("Début du test : testDeleteFireStation pour l'adresse " + address);

        mockMvc.perform(delete("/firestation")
                .param("address", address)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Fire station mapping successfully deleted."));

        // Log et print pour confirmer la fin du test
        logger.info("Fin du test : testDeleteFireStation pour l'adresse {}", address);
        System.out.println("Fin du test : testDeleteFireStation pour l'adresse " + address);
    }
}
