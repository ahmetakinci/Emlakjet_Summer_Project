package com.example.emlakjet_summer_project.response;

import com.example.emlakjet_summer_project.entitiy.enums.AdvertType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdvertResponse {
    private String id;

    private AdvertType type;
    
    private String title;

    private String description;

    private String price;

    private String roomNumber;

    private String grossM;

    private String netM;

}
