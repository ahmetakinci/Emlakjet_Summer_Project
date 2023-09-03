package com.example.emlakjet_summer_project.entitiy;

import com.example.emlakjet_summer_project.entitiy.enums.Role;
import com.example.emlakjet_summer_project.entitiy.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String surName;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(unique = true,nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "advertiser")
    private List<AdvertEntity> adverts;

    public PersonEntity(String name, String surName, String email, String phoneNumber, String password, Status status, Role role) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.surName = surName;
        this.status = status;
        this.role = role;

    }
}
