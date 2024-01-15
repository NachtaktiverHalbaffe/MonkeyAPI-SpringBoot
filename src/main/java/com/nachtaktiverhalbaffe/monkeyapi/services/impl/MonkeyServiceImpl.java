package com.nachtaktiverhalbaffe.monkeyapi.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nachtaktiverhalbaffe.monkeyapi.domain.ImageData;
import com.nachtaktiverhalbaffe.monkeyapi.domain.Monkey;
import com.nachtaktiverhalbaffe.monkeyapi.domain.Species;
import com.nachtaktiverhalbaffe.monkeyapi.repositories.MonkeyRepository;
import com.nachtaktiverhalbaffe.monkeyapi.services.MonkeyService;
import com.nachtaktiverhalbaffe.monkeyapi.services.SpeciesService;

@Service
public class MonkeyServiceImpl implements MonkeyService {

    private MonkeyRepository monkeyRepository;
    private ImageDataService imageDataService;
    private SpeciesService speciesService;

    @Autowired
    public MonkeyServiceImpl(MonkeyRepository monkeyRepository, ImageDataService imageDataService,
            SpeciesService speciesService) {
        this.monkeyRepository = monkeyRepository;
        this.imageDataService = imageDataService;
        this.speciesService = speciesService;
    }

    @Override
    public Optional<Monkey> create(Optional<MultipartFile> image, Monkey monkey, Optional<String> speciesName) {

        if (monkeyRepository.existsByName(monkey.getName())) {
            return Optional.empty();
        } else {
            Monkey monkeyToSave = monkey;

            if (image.isPresent()) {
                Optional<ImageData> storedImage = imageDataService.storeFile(image.get());
                monkeyToSave.setImage(storedImage.isPresent() ? storedImage.get() : null);
            } // TODO call pexels api for random image

            // Fetch species from corresponding service if species name is specified and
            // request doesnt include species data
            if (speciesName.isPresent() && monkey.getSpecies() == null) {
                Optional<Species> foundSpecies = speciesService.getByName(speciesName.get());
                monkeyToSave.setSpecies(foundSpecies.isPresent() ? foundSpecies.get() : null);
            }

            return Optional.of(monkeyRepository.save(monkeyToSave));
        }
    }

    @Override
    public List<Monkey> getAll() {
        return StreamSupport.stream(monkeyRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Page<Monkey> getAll(Pageable pageable) {
        return monkeyRepository.findAll(pageable);
    }

    @Override
    public Optional<Monkey> get(String name) {
        return monkeyRepository.findByName(name);
    }

    @Override
    public Optional<Monkey> get(Long id) {
        return monkeyRepository.findById(id);
    }

    @Override
    public Optional<Monkey> update(Optional<MultipartFile> image, Monkey monkey) {

        Monkey existingMonkey;
        if (Optional.ofNullable(monkey.getId()).isPresent()) {
            Optional<Monkey> foundMonkey = monkeyRepository.findById(monkey.getId());
            if (foundMonkey.isPresent()) {
                existingMonkey = foundMonkey.get();
            } else {
                return Optional.empty();
            }
        } else if (Optional.ofNullable(monkey.getName()).isPresent()) {
            Optional<Monkey> foundMonkey = monkeyRepository.findByName(monkey.getName());
            if (foundMonkey.isPresent()) {
                existingMonkey = foundMonkey.get();
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }

        if (image.isPresent()) {
            Optional<ImageData> updatedImage = imageDataService.storeFile(image.get());
            existingMonkey.setImage(updatedImage.isPresent() ? updatedImage.get() : existingMonkey.getImage());
        }

        Optional.ofNullable(monkey.getName()).ifPresent(existingMonkey::setName);
        Optional.ofNullable(monkey.getKnown_from()).ifPresent(existingMonkey::setKnown_from);
        Optional.ofNullable(monkey.getDescription()).ifPresent(existingMonkey::setDescription);
        Optional.ofNullable(monkey.getStrength()).ifPresent(existingMonkey::setStrength);
        Optional.ofNullable(monkey.getWeaknesses()).ifPresent(existingMonkey::setWeaknesses);
        if (monkey.getAttack() != 0)
            existingMonkey.setAttack(monkey.getAttack());
        if (monkey.getDefense() != 0)
            existingMonkey.setDefense(monkey.getDefense());
        if (monkey.getSpecialAttack() != 0)
            existingMonkey.setSpecialAttack(monkey.getSpecialAttack());
        if (monkey.getSpecialDefense() != 0)
            existingMonkey.setSpecialDefense(monkey.getSpecialDefense());
        if (monkey.getSpeed() != 0)
            existingMonkey.setSpeed(monkey.getSpeed());
        if (monkey.getHealthPoints() != 0)
            existingMonkey.setHealthPoints(monkey.getHealthPoints());
        Optional.ofNullable(monkey.getSpecies()).ifPresent(existingMonkey::setSpecies);

        return Optional.of(monkeyRepository.save(existingMonkey));

    }

    @Override
    public void delete(String name) {
        monkeyRepository.deleteByName(name);
    }

    @Override
    public void delete(Long id) {
        monkeyRepository.deleteById(id);
    }

    @Override
    public void delete(Monkey monkey) {
        monkeyRepository.delete(monkey);
    }

    @Override
    public boolean exists(String name) {
        if (name != null) {
            return monkeyRepository.existsByName(name);
        } else {
            return false;
        }
    }

    @Override
    public boolean exists(Monkey monkey) {
        return exists(monkey.getId()) || exists(monkey.getName());
    }

    @Override
    public boolean exists(Long id) {
        if (id != null) {
            return monkeyRepository.existsById(id);
        } else {
            return false;
        }

    }

}
