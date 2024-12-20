package com.openclassroom.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassroom.api.model.ChildAlert;
import com.openclassroom.api.service.ChildAlertService;

import java.util.Map;

@RestController
public class ChildAlertController {

    @Autowired
    private ChildAlertService childAlertService;

    /**
     * Endpoint pour récupérer la liste des enfants et des membres du foyer à une adresse donnée.
     * http://localhost:8080/childAlert?address=<address>
     *
     * @param address L'adresse à interroger
     * @return Les informations des enfants et des membres du foyer
     */
    @GetMapping("/childAlert")
    public ChildAlert getChildrenByAddress(@RequestParam String address) {
        return childAlertService.getChildrenByAddress(address);
    }
}
