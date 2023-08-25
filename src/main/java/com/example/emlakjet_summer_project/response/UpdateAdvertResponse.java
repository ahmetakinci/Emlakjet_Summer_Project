package com.example.emlakjet_summer_project.response;

import com.example.emlakjet_summer_project.entitiy.enums.AdvertType;
import com.example.emlakjet_summer_project.entitiy.enums.RoomNumber;
import com.example.emlakjet_summer_project.entitiy.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdvertResponse {
    private String id;

    private AdvertType type;

    private String title;

    private String description;

    private String price;

    private RoomNumber roomNumber;

    private String grossM;

    private String netM;

    private Status status;
}
