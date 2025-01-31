package com.openclassroom.api.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassroom.api.service.FloodService;

@RestController
public class FloodController {

    @Autowired
    private FloodService floodService;

    /**
     * Endpoint pour obtenir la liste des foyers desservis par les casernes.
     * http://localhost:8080/flood/stations?stations=<list_of_station_numbers>
     * 
     * @param stationNumbers Liste des numéros de caserne séparés par des virgules.
     * @return Les foyers regroupés par adresse, avec les informations des habitants.
     */
    @GetMapping("/flood/stations")
    public Map<String, List<Map<String, Object>>> getHouseholdsByStations(@RequestParam String stations) {
        // Convertir la liste de stations en liste d'entiers
        List<Integer> stationNumbers = Arrays.stream(stations.split(",")) //Séparer la chaine par des virgules
                .map(String::trim) //Supprimer les espaces
                .map(Integer::parseInt) //Convertir chaque element en entier
                .collect(Collectors.toList()); //Collect les résultat sous la forme d'un liste

        //Appel du service pour récupérer les foyer par numéro de station
        return floodService.getHouseholdsByStations(stationNumbers);
    }
}