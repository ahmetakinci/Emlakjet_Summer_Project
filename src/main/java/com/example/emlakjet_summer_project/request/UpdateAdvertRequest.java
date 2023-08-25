package com.example.emlakjet_summer_project.request;

import com.example.emlakjet_summer_project.entitiy.enums.AdvertType;
import com.example.emlakjet_summer_project.entitiy.enums.RoomNumber;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UpdateAdvertRequest {

    private String id;

    private AdvertType type;

    private String title;

    private String description;

    private String price;

    private RoomNumber roomNumber;

    private String grossM;

    private String netM;
}
