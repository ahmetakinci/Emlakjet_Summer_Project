package com.example.emlakjet_summer_project.controller;

import com.example.emlakjet_summer_project.entitiy.enums.AdvertType;
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
@RequestMapping("/api/advert")
public class AdvertController {
    private final AdvertService advertService;

    public AdvertController(AdvertService advertService) {
        this.advertService = advertService;
    }
    @PostMapping("/user/create")
    public ResponseEntity<CreateAdvertResponse> createAdvert(@RequestBody CreateAdvertRequest request){
        return new ResponseEntity<>(advertService.createAdvert(request), HttpStatus.CREATED);
    }
    @PutMapping("/user/update")
    public ResponseEntity<UpdateAdvertResponse> updateAdvert(@RequestBody UpdateAdvertRequest request){
        return new ResponseEntity<>(advertService.updateAdvert(request),HttpStatus.CREATED);
    }
    @PatchMapping("/user/{id}/{status}")
    public ResponseEntity<UpdateAdvertResponse> updateStatusAdvert(@PathVariable("id") String id,
                                                                   @PathVariable("status") Status status){
        return new ResponseEntity<>(advertService.updateStatusAdvert(id, status),HttpStatus.CREATED);
    }
    @DeleteMapping("/user/{id}/delete")
    public ResponseEntity<Void> deleteAdvert(@PathVariable("id")String id){
        advertService.deleteAdvert(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<GetAdvertResponse>> getAllAdvert(){
        return new ResponseEntity<>(advertService.getAllAdvert(),HttpStatus.OK);
    }
    @GetMapping("/search/{query}")
    public ResponseEntity<List<GetAdvertResponse>> search(@PathVariable String query){
        return new ResponseEntity<>(advertService.search(query),HttpStatus.OK);
    }
    @GetMapping("/filter/{type}")
    public ResponseEntity<List<GetAdvertResponse>> filterAdvertType(@PathVariable AdvertType type){
        return new ResponseEntity<>(advertService.filterAdvertType(type),HttpStatus.OK);
    }
    @GetMapping("/filterBetweenPrice")
    public ResponseEntity<List<GetAdvertResponse>> filterBetweenPrice(@RequestParam("minPrice") String minPrice,
                                                                      @RequestParam("maxPrice") String maxPrice){
        return new ResponseEntity<>(advertService.filterBetweenPrice(minPrice,maxPrice),HttpStatus.OK);
    }
}
