package com.example.emlakjet_summer_project.entitiy;

import com.example.emlakjet_summer_project.entitiy.enums.AdvertType;
import com.example.emlakjet_summer_project.entitiy.enums.RoomNumber;
import com.example.emlakjet_summer_project.entitiy.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AdvertEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private AdvertType type;

    private String title;

    private String description;

    private String price;

    @Enumerated(EnumType.STRING)
    private RoomNumber roomNumber;

    private String grossM;

    private String netM;

    @Enumerated(EnumType.STRING)
    private Status status;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    private PersonEntity advertiser;

    public AdvertEntity(AdvertType type, String title,
                        String description, String price,
                        RoomNumber roomNumber, String grossM,
                        String netM, Status status, PersonEntity person) {
        this.type = type;
        this.title = title;
        this.description = description;
        this.price = price;
        this.roomNumber = roomNumber;
        this.grossM = grossM;
        this.netM = netM;
        this.status = status;
        this.advertiser = person;
    }
}

