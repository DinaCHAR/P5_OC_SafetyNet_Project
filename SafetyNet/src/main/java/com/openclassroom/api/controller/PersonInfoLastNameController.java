package com.openclassroom.api.controller;

import com.openclassroom.api.service.PersonInfoLastNameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class PersonInfoLastNameController {

    @Autowired
    private PersonInfoLastNameService personInfoLastNameService;

    /**
     * Endpoint pour récupérer les informations des personnes ayant un certain nom de famille.
     * http://localhost:8080/personInfolastName=<lastName>
     *
     * @param lastName Nom de famille à rechercher.
     * @return Liste d'informations des personnes portant ce nom de famille.
     */
    @GetMapping("/personInfolastName")
    public List<Map<String, Object>> getPersonInfoByLastName(@RequestParam String lastName) {
        return personInfoLastNameService.getPersonInfoByLastName(lastName);
    }
}
