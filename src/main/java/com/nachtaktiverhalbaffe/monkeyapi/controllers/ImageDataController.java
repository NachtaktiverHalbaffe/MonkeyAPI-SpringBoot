package com.nachtaktiverhalbaffe.monkeyapi.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nachtaktiverhalbaffe.monkeyapi.domain.ImageData;
import com.nachtaktiverhalbaffe.monkeyapi.services.impl.ImageDataService;

@RestController
@RequestMapping("api/v1/imagepool")
public class ImageDataController {

    @Autowired
    private ImageDataService imageDataService;

    @PostMapping
    public ResponseEntity<Void> uploadImage(@RequestParam("image") MultipartFile file) {
        if (imageDataService.exists(file)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        boolean operationSuccessful = imageDataService.storeFile(file);
        if (operationSuccessful) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<Void> modifyImage(@RequestParam("image") MultipartFile file) {
        boolean operationSuccessful = imageDataService.storeFile(file);
        if (operationSuccessful) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImageByName(@PathVariable("fileName") String fileName) {
        Optional<ImageData> image = imageDataService.loadFile(fileName);

        if (image.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(image.get().getType()))
                    .body(image.get().getData());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
