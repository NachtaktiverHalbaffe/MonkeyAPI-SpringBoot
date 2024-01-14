package com.nachtaktiverhalbaffe.monkeyapi.services;

import java.io.IOException;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService<T> {

    Optional<T> loadFile(String name);

    boolean storeFile(MultipartFile file);

    boolean exists(MultipartFile file);

    boolean existsByName(String name);
}
