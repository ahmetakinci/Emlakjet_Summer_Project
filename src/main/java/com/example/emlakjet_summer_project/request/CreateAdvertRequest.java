package com.example.emlakjet_summer_project.request;

import com.example.emlakjet_summer_project.entitiy.enums.AdvertType;
import com.example.emlakjet_summer_project.entitiy.enums.RoomNumber;
import lombok.Data;

@Data
public class CreateAdvertRequest {
    private AdvertType type;

    private String title;

    private String description;

    private String price;

    private RoomNumber roomNumber;

    private String grossM;

    private String netM;

    private String personId;
}
