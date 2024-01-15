package com.nachtaktiverhalbaffe.monkeyapi.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nachtaktiverhalbaffe.monkeyapi.domain.Monkey;
import com.nachtaktiverhalbaffe.monkeyapi.domain.dto.MonkeyDto;
import com.nachtaktiverhalbaffe.monkeyapi.services.MonkeyService;
import com.nachtaktiverhalbaffe.monkeyapi.services.impl.ImageDataService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping(path = "api/v1/monkeys")
public class MonkeyController {

    private MonkeyService monkeyService;
    private Environment environment;

    @Autowired
    public MonkeyController(MonkeyService monkeyService, Environment environment) {
        this.monkeyService = monkeyService;
        this.environment = environment;
    }

    @GetMapping
    public ResponseEntity<Page<MonkeyDto>> getAllMonkeys(Pageable pageable) {
        Page<Monkey> monkeys = monkeyService.getAll(pageable);
        return new ResponseEntity<>(monkeys.map(element -> new MonkeyDto(element, environment)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonkeyDto> getMonkeybyId(@PathVariable("id") long id) {
        Optional<Monkey> monkey = monkeyService.get(id);

        if (monkey.isPresent()) {
            return new ResponseEntity<>(new MonkeyDto(monkey.get(), environment), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by_name/{name}")
    public ResponseEntity<MonkeyDto> getMonkeybyName(@PathVariable("name") String name) {
        Optional<Monkey> monkey = monkeyService.get(name);

        if (monkey.isPresent()) {
            return new ResponseEntity<>(new MonkeyDto(monkey.get(), environment), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<MonkeyDto> createNewMonkey(@RequestParam(name = "body") String jsonPayload,
            @RequestParam(name = "image", required = false) MultipartFile file) {
        try {
            MonkeyDto payload = new MonkeyDto(jsonPayload);
            if (monkeyService.exists(payload.getName())) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            Optional<Monkey> savedMonkey = monkeyService.create(Optional.ofNullable(file), payload.mapToEntity(),
                    Optional.ofNullable(payload.getSpecies_name()));
            if (savedMonkey.isPresent()) {
                return new ResponseEntity<>(new MonkeyDto(savedMonkey.get(), environment), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping
    public ResponseEntity<MonkeyDto> updateMonkey(@RequestParam(name = "body", required = true) String jsonPayload,
            @RequestParam(name = "image", required = false) MultipartFile file) {
        try {
            MonkeyDto payload = new MonkeyDto(jsonPayload);
            if (!monkeyService.exists(payload.getId()) || !monkeyService.exists(payload.getName())) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Optional<Monkey> updatedMonkey = monkeyService.update(Optional.ofNullable(file),
                    payload.mapToEntity());
            if (updatedMonkey.isPresent()) {
                return new ResponseEntity<>(new MonkeyDto(updatedMonkey.get(), environment), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<MonkeyDto> updateMonkeyById(
            @RequestParam(name = "body", required = false) String jsonPayload,
            @RequestParam(name = "image", required = false) MultipartFile file, @PathVariable("id") Long id) {

        try {
            MonkeyDto payload = Optional.ofNullable(jsonPayload).isPresent() ? new MonkeyDto(jsonPayload)
                    : new MonkeyDto();
            if (!monkeyService.exists(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Monkey updateData = payload.mapToEntity();
            updateData.setId(id);

            Optional<Monkey> updatedMonkey = monkeyService.update(Optional.ofNullable(file),
                    updateData);
            if (updatedMonkey.isPresent()) {
                return new ResponseEntity<>(new MonkeyDto(updatedMonkey.get(), environment),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }

    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> deleteMonkeyById(@PathVariable("id") Long id) {
        monkeyService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/by_name/{name}")
    public ResponseEntity<Void> deleteMonkeyByName(@PathVariable("name") String name) {
        monkeyService.delete(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMonkey(@RequestBody MonkeyDto monkeyDto) {
        monkeyService.delete(monkeyDto.mapToEntity());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
