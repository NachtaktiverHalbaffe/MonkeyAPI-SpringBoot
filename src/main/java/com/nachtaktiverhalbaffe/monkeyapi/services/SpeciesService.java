package com.nachtaktiverhalbaffe.monkeyapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nachtaktiverhalbaffe.monkeyapi.domain.Species;

public interface SpeciesService {

    public Species createSpecies(Species species);

    public List<Species> getAll();

    public Page<Species> getAll(Pageable pageable);

    public Optional<Species> getByName(String name);

    public Species update(Species species);

    public void deleteByName(String name);

    public void delete(Species species);

    public boolean exists(String name);

}
