package com.nachtaktiverhalbaffe.monkeyapi.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nachtaktiverhalbaffe.monkeyapi.domain.Species;
import com.nachtaktiverhalbaffe.monkeyapi.repositories.SpeciesRepository;
import com.nachtaktiverhalbaffe.monkeyapi.services.SpeciesService;

import jakarta.transaction.Transactional;

@Service
public class SpeciesServiceImpl implements SpeciesService {

    private SpeciesRepository repository;

    public SpeciesServiceImpl(SpeciesRepository repository) {
        this.repository = repository;
    }

    @Override
    public Species createSpecies(Species species) {
        return this.repository.save(species);
    }

    @Override
    public List<Species> getAll() {
        List<Species> result = new ArrayList<>();
        this.repository.findAll().forEach(element -> result.add(element));

        return result;
    }

    @Override
    public Page<Species> getAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public Optional<Species> getByName(String name) {
        return this.repository.findByName(name);
    }

    @Override
    public Species update(Species species) throws RuntimeException {
        Species existingEntity = this.repository.findByName(species.getName())
                .orElseThrow(() -> new RuntimeException("Species does not exist"));

        Optional.ofNullable(species.getTaxonomy()).ifPresent(existingEntity::setTaxonomy);
        Optional.ofNullable(species.getCharacteristics()).ifPresent(existingEntity::setCharacteristics);
        Optional.ofNullable(species.getLocations()).ifPresent(existingEntity::setLocations);

        return this.repository.save(existingEntity);
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        this.repository.deleteByName(name);
    }

    @Override
    @Transactional
    public void delete(Species species) {
        this.repository.delete(species);
    }

    @Override
    public boolean exists(String name) {
        // TODO Auto-generated method stub
        return this.repository.existsByName(name);
    }

}
