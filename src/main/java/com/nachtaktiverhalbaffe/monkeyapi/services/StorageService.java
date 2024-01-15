package com.nachtaktiverhalbaffe.monkeyapi.services;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService<T> {

    Optional<T> loadFile(String name);

    Optional<T> storeFile(MultipartFile file);

    boolean exists(MultipartFile file);

    boolean existsByName(String name);
}
