package com.openclassroom.api.controller;

import com.openclassroom.api.model.PersonByFirestation;
import com.openclassroom.api.service.FireStationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/firestation")
public class FireStationController {
	

    @Autowired
    private FireStationService fireStationService;

    /**
	 * Endpoint pour récupérer les informations des personnes couvertes par une station de pompiers donnée.
	 * http://localhost:8080/firestation?stationNumber=<station_number>
	 * 
	 * @param stationNumber Le numéro de la station de pompiers envoyé en tant que paramètre de requête.
	 * @return Une Map contenant :
	 * La liste des personnes avec prénom, nom, adresse et téléphone.
	 * Calucluer l'âge des adultes et des enfants.
	 */
    @GetMapping //Indiquer que c'est une méthode GET
    public PersonByFirestation getPersonsCoveredByStation(@RequestParam String stationNumber) {
    	// Appel au service pour récupérer les données et renvoyer les résultats
        return fireStationService.getPersonsCoveredByStation(stationNumber);
        
    }

}
