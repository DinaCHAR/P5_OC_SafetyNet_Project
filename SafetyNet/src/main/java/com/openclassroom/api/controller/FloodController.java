package com.openclassroom.api.controller;

import java.util.List;
import java.util.Map;

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
    public Map<String, List<Map<String, Object>>> getHouseholdsByStations(@RequestParam List<Integer> stations) {
        return floodService.getHouseholdsByStations(stations);
    }
}