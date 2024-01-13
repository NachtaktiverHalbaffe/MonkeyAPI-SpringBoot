package com.nachtaktiverhalbaffe.monkeyapi.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nachtaktiverhalbaffe.monkeyapi.domain.Species;
import com.nachtaktiverhalbaffe.monkeyapi.repositories.SpeciesRepository;
import com.nachtaktiverhalbaffe.monkeyapi.services.SpeciesService;

@Service
public class SpeciesServiceImpl implements SpeciesService {

    private SpeciesRepository repository;

    public SpeciesServiceImpl(SpeciesRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createSpecies(Species species) {
        this.repository.save(species);
    }

    @Override
    public List<Species> getAllSpecies() {
        List<Species> result = new ArrayList<>();
        this.repository.findAll().forEach(element -> result.add(element));

        return result;
    }

    @Override
    public Optional<Species> getSpeciesbyName(String name) {
        return this.repository.findByName(name);
    }

    @Override
    public Optional<Species> getSpeciesById(Long id) {
        // TODO integrate API to fetch animals from online resource
        return this.repository.findById(id);

    }

    @Override
    public void updateSpecies(Species species) {
        this.repository.save(species);
    }

    @Override
    public void deleteSpeciesById(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    public void deleteSpeciesByName(String name) {
        this.repository.deleteByName(name);
    }

    @Override
    public void deleteSpecies(Species species) {
        this.repository.delete(species);
    }

}
