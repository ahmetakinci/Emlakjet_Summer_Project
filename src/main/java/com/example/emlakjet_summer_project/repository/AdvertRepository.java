package com.example.emlakjet_summer_project.repository;

import com.example.emlakjet_summer_project.entitiy.AdvertEntity;
import com.example.emlakjet_summer_project.entitiy.enums.AdvertType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertRepository extends JpaRepository<AdvertEntity, String> {
    List<AdvertEntity> findByDescriptionLikeOrTitleLike(String description, String title);

    List<AdvertEntity> findByType(AdvertType type);

    List<AdvertEntity> findByPriceBetween(String minPrice, String maxPrice);

}
