package com.example.emlakjet_summer_project.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePersonRequest {
    private String name;

    private String surName;

    private String email;

    private String phoneNumber;

    private String password;
}
