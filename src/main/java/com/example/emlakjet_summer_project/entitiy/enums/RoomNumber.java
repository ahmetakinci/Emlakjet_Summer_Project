package com.example.emlakjet_summer_project.entitiy.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoomNumber {
    ONE_PLUS_ZERO("1+0"),
    ONE_PLUS_ONE("1+1"),
    TWO_PLUS_ONE("2+1"),
    THREE_PLUS_ONE("3+1"),
    UNKNOWN("Unknown");
    private final String roomNumber;
}
