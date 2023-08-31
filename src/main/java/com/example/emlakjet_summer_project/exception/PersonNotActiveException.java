package com.example.emlakjet_summer_project.exception;

public class PersonNotActiveException extends RuntimeException {
    public PersonNotActiveException(String message ) {
        super(message);
    }
}
