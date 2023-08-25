package com.example.emlakjet_summer_project.controller;

import com.example.emlakjet_summer_project.request.CreatePersonRequest;
import com.example.emlakjet_summer_project.response.CreatePersonResponse;
import com.example.emlakjet_summer_project.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/persons")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<CreatePersonResponse> createPerson(@RequestBody CreatePersonRequest request){
        return new ResponseEntity<>(personService.createPerson(request), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable("id") String id){
        personService.deletePerson(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
