package com.openclassroom.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassroom.api.model.PersonByFirestation;
import com.openclassroom.api.service.FireService;

@RestController
public class FireController {

    @Autowired
    private FireService fireService;

    /**
     * Endpoint pour récupérer la liste des habitants vivants à l’adresse donnée 
     * ainsi que le numéro de la caserne de pompiers la desservant.
     * http://localhost:8080/fire?address=<address>
     *
     * @param address L'adresse à interroger
     * @return Les informations des habitants, du nombre d'enfants, d'adultes, 
     *         et du numéro de la caserne de pompiers
     */
    @GetMapping("/fire")
    public PersonByFirestation getPersonsByAddress(@RequestParam String address) {
        return fireService.getPersonsByAddress(address);
    }
}
