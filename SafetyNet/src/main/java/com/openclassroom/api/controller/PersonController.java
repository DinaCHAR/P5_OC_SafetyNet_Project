package com.openclassroom.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassroom.api.model.Person;
import com.openclassroom.api.service.PersonService;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/persons")
    public List<Person> getPersons (){
    	return personService.getPersons();
    }
    // Endpoint pour ajouter une nouvelle personne
    @PostMapping("/person")
    public ResponseEntity<String> addPerson(@RequestBody Person person) {
        boolean added = personService.addPerson(person);
        if (added) {
            return ResponseEntity.ok("Person successfully added.");
        } else {
            return ResponseEntity.badRequest().body("Person already exists.");
        }
    }

    // Endpoint mettre a jour une nouvelle personne
    @PutMapping("/person")
    public ResponseEntity<String> updatePerson(@RequestBody Person person) {
        Person updated = personService.updatePerson(person);
        if (updated != null) {
            return ResponseEntity.ok("Person successfully updated.");
        } else {
            return ResponseEntity.badRequest().body("Person not found.");
        }
    }

    // Endpoint supp une personne avec nom et prenom
    @DeleteMapping("/person")
    public ResponseEntity<String> deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        boolean deleted = personService.deletePerson(firstName, lastName);
        if (deleted) {
            return ResponseEntity.ok("Person successfully deleted.");
        } else {
            return ResponseEntity.badRequest().body("Person not found.");
        }
    }
}