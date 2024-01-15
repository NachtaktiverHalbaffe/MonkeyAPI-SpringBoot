package com.nachtaktiverhalbaffe.monkeyapi.services.impl;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nachtaktiverhalbaffe.monkeyapi.domain.ImageData;
import com.nachtaktiverhalbaffe.monkeyapi.repositories.ImageDataRepository;
import com.nachtaktiverhalbaffe.monkeyapi.services.StorageService;
import com.nachtaktiverhalbaffe.monkeyapi.util.ImageUtils;

import jakarta.transaction.Transactional;

@Service
public class ImageDataService implements StorageService<ImageData> {

    @Autowired
    private ImageDataRepository imageDataRepository;

    @Transactional
    @Override
    public Optional<ImageData> loadFile(String name) {
        Optional<ImageData> image = imageDataRepository.findByName(name);

        if (image.isPresent()) {
            image.get().setData(ImageUtils.decompressImage(image.get().getData()));
        }

        return image;
    }

    @Transactional
    @Override
    public Optional<ImageData> storeFile(MultipartFile file) {
        ImageData image = new ImageData();
        try {
            image = ImageData.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .fileSize(file.getSize())
                    .data(ImageUtils.compressImage(file.getBytes()))
                    .build();

        } catch (IOException e) {
            return Optional.empty();
        }

        ImageData savedImage = imageDataRepository.save(image);
        return Optional.of(savedImage);
    }

    @Override
    public boolean exists(MultipartFile file) {
        return existsByName(file.getOriginalFilename());
    }

    @Override
    public boolean existsByName(String name) {
        return imageDataRepository.existsByName(name);
    }

}
