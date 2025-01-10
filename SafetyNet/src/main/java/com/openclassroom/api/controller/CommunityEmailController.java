package com.openclassroom.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassroom.api.service.CommunityEmailService;

import java.util.List;

@RestController
public class CommunityEmailController {

    @Autowired
    private CommunityEmailService communityEmailService;

    /**
     * Endpoint pour récupérer les adresses email des habitants d'une ville donnée.
     * Exemple : http://localhost:8080/communityEmail?city=<city>
     *
     * @param city La ville pour laquelle récupérer les emails
     * @return La liste des adresses email
     */
    @GetMapping("/communityEmail")
    public List<String> getEmailsByCity(@RequestParam String city) {
        return communityEmailService.getEmailsByCity(city);
    }
}
