package com.example.emlakjet_summer_project.response;

import com.example.emlakjet_summer_project.entitiy.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePersonResponse {
    private String id;

    private String name;

    private String surName;

    private String email;

    private String phoneNumber;

    private String password;

    private Status status;

}
