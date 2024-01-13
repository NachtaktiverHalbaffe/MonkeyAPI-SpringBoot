package com.nachtaktiverhalbaffe.monkeyapi.services;

import java.util.List;
import java.util.Optional;

import com.nachtaktiverhalbaffe.monkeyapi.domain.Species;

public interface SpeciesService {

    public void createSpecies(Species species);

    public List<Species> getAllSpecies();

    public Optional<Species> getSpeciesbyName(String name);

    public Optional<Species> getSpeciesById(Long id);

    public void updateSpecies(Species species);

    public void deleteSpeciesById(Long id);

    public void deleteSpeciesByName(String name);

    public void deleteSpecies(Species species);

}
