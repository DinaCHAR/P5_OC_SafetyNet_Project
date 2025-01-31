package com.openclassroom.api.controller;

import com.openclassroom.api.model.FireStations;
import com.openclassroom.api.model.PersonByFirestation;
import com.openclassroom.api.service.FireStationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


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
    
    /**
     * Ajoute un nouveau mapping entre une adresse et une caserne.
     * http://localhost:8080/firestation (POST)
     *
     * @param address L'adresse à associer à la caserne.
     * @param station Le numéro de la caserne.
     * @return FireStations ajouté ou null si un mapping existe déjà.
     */
    @PostMapping
    public ResponseEntity<String> addFireStation(@RequestParam String address, @RequestParam int station) {
        FireStations result = fireStationService.addFireStationMapping(address, station);
        if (result != null) {
            return ResponseEntity.ok("Fire station mapping successfully added.");
        } else {
            return ResponseEntity.badRequest().body("Mapping already exists for this address.");
        }
    }

    /**
     * Met à jour le numéro de la caserne pour une adresse donnée.
     * http://localhost:8080/firestation (PUT)
     *
     * @param address L'adresse concernée.
     * @param newStation Le nouveau numéro de caserne.
     * @return FireStations mis à jour ou null si l'adresse n'existe pas.
     */
    @PutMapping
    public ResponseEntity<String> updateFireStation(@RequestParam String address, @RequestParam String newStation) {
        FireStations result = fireStationService.updateFireStationMapping(address, newStation);
        if (result != null) {
            return ResponseEntity.ok("Fire station mapping successfully updated.");
        } else {
            return ResponseEntity.badRequest().body("Address not found for mapping update.");
        }
    }

    /**
     * Supprime un mapping entre une adresse et une caserne.
     * http://localhost:8080/firestation (DELETE)
     *
     * @param address L'adresse à supprimer.
     * @return true si supprimé, false si l'adresse n'existe pas.
     */
    @DeleteMapping
    public ResponseEntity<String> deleteFireStation(@RequestParam String address) {
        boolean result = fireStationService.deleteFireStationMapping(address);
        if (result) {
            return ResponseEntity.ok("Fire station mapping successfully deleted.");
        } else {
            return ResponseEntity.badRequest().body("Address not found for mapping deletion.");
        }
    }
}
