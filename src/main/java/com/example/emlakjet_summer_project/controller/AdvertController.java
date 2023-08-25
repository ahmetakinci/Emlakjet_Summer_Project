package com.example.emlakjet_summer_project.controller;

import com.example.emlakjet_summer_project.entitiy.enums.Status;
import com.example.emlakjet_summer_project.request.CreateAdvertRequest;
import com.example.emlakjet_summer_project.request.UpdateAdvertRequest;
import com.example.emlakjet_summer_project.response.CreateAdvertResponse;
import com.example.emlakjet_summer_project.response.GetAdvertResponse;
import com.example.emlakjet_summer_project.response.UpdateAdvertResponse;
import com.example.emlakjet_summer_project.service.AdvertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/adverts")
public class AdvertController {
    private final AdvertService advertService;

    public AdvertController(AdvertService advertService) {
        this.advertService = advertService;
    }
    @PostMapping
    public ResponseEntity<CreateAdvertResponse> createAdvert(@RequestBody CreateAdvertRequest request){
        return new ResponseEntity<>(advertService.createAdvert(request), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<UpdateAdvertResponse> updateAdvert(@RequestBody UpdateAdvertRequest request){
        return new ResponseEntity<>(advertService.updateAdvert(request),HttpStatus.CREATED);
    }
    @PatchMapping("/{id}/{status}")
    public ResponseEntity<UpdateAdvertResponse> updateStatusAdvert(@PathVariable("id") String id,
                                                                   @PathVariable("status") Status status){
        return new ResponseEntity<>(advertService.updateStatusAdvert(id, status),HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdvert(@PathVariable("id")String id){
        advertService.deleteAdvert(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<GetAdvertResponse>> getAllAdvert(){
        return new ResponseEntity<>(advertService.getAllAdvert(),HttpStatus.OK);
    }
}
