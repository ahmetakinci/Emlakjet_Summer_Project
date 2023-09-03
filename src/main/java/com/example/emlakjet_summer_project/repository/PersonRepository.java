package com.example.emlakjet_summer_project.repository;

import com.example.emlakjet_summer_project.entitiy.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<PersonEntity, String> {

    Boolean existsByEmail(String email);

    Boolean existsByPhoneNumber(String phoneNumber);

    Optional<PersonEntity> findByEmail(String email);
}
