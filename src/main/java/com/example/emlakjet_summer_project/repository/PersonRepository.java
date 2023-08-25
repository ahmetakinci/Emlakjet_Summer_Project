package com.example.emlakjet_summer_project.repository;

import com.example.emlakjet_summer_project.entitiy.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person,String> {

    Boolean existsByEmail(String email);

    Boolean existsByPhoneNumber(String phoneNumber);
}
