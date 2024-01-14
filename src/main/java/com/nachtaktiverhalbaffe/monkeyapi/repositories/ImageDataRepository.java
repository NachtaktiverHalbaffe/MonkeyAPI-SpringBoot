package com.nachtaktiverhalbaffe.monkeyapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nachtaktiverhalbaffe.monkeyapi.domain.ImageData;

public interface ImageDataRepository extends JpaRepository<ImageData, String> {

    Optional<ImageData> findByName(String name);

    boolean existsByName(String name);

}
