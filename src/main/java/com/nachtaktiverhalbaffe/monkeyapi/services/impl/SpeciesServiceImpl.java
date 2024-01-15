package com.nachtaktiverhalbaffe.monkeyapi.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.nachtaktiverhalbaffe.monkeyapi.config.ApiKeys;
import com.nachtaktiverhalbaffe.monkeyapi.domain.Species;
import com.nachtaktiverhalbaffe.monkeyapi.repositories.SpeciesRepository;
import com.nachtaktiverhalbaffe.monkeyapi.services.SpeciesService;

import jakarta.transaction.Transactional;

@Service
public class SpeciesServiceImpl implements SpeciesService {

    private SpeciesRepository repository;
    private final RestClient restClient;
    private ApiKeys apiKeys;

    public SpeciesServiceImpl(SpeciesRepository repository, ApiKeys animalsAPIConfig) {
        this.repository = repository;
        this.apiKeys = animalsAPIConfig;
        restClient = RestClient.builder().baseUrl("https://api.api-ninjas.com").build();
    }

    @Override
    public Species createSpecies(Species species) {
        return this.repository.save(species);
    }

    @Override
    public List<Species> getAll() {
        return StreamSupport.stream(this.repository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Page<Species> getAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public Optional<Species> getByName(String name) {
        Optional<Species> speciesFromDb = this.repository.findByName(name);

        if (speciesFromDb.isPresent()) {
            return speciesFromDb;
        } else {
            // Retrieve information from external api
            List<Species> restResponses = restClient.get()
                    .uri("/v1/animals?name={name}", name)
                    .header("X-Api-Key", apiKeys.getAnimalsApi())
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<Species>>() {
                    });
            if (restResponses == null) {
                return Optional.empty();
            } else if (restResponses.size() == 1) {
                return Optional.of(restResponses.get(0));
            } else if (!restResponses.isEmpty()) {
                // Check which of the received elements is the nearest to the asked species
                int bestSimilarityScore = 1000;
                Species nearestElement = null;

                for (Species element : restResponses) {
                    if (element.getName().equals(name)) {
                        return Optional.of(element);
                    } else {
                        int similarityScore = LevenshteinDistance.getDefaultInstance().apply(name, element.getName());
                        if (similarityScore < bestSimilarityScore) {
                            bestSimilarityScore = similarityScore;
                            nearestElement = element;
                        }
                    }
                }
                return Optional.of(nearestElement);
            } else {
                return Optional.empty();
            }
        }
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
        return this.repository.existsByName(name);
    }

}
