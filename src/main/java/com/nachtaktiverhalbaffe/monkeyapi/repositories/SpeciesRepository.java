package com.nachtaktiverhalbaffe.monkeyapi.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.nachtaktiverhalbaffe.monkeyapi.domain.Species;

@Repository
public interface SpeciesRepository
        extends CrudRepository<Species, String>, PagingAndSortingRepository<Species, String> {

    Optional<Species> findByName(String name);

    void deleteByName(String name);

    boolean existsByName(String name);

}
