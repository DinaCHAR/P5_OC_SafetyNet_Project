package com.openclassroom.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassroom.api.service.PhoneAlertService;

@RestController
public class PhoneAlertController {

    @Autowired
    private PhoneAlertService phoneAlertService;

    /**
     * Endpoint pour récupérer la liste des numéros de téléphone des personnes desservies par une caserne de pompiers.
     * http://localhost:8080/phoneAlert?firestation=<firestation_number>
     *
     * @param firestationNumber Le numéro de la caserne de pompiers à interroger
     * @return La liste des numéros de téléphone des personnes desservies par la caserne spécifiée
     */
    @GetMapping("/phoneAlert")
    public List<String> getPhoneNumbers(@RequestParam("firestation") String firestationNumber) {
        return phoneAlertService.getPhoneNumbersByFirestation(firestationNumber);
    }
}