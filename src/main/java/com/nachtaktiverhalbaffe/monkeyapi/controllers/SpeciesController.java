package com.nachtaktiverhalbaffe.monkeyapi.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.nachtaktiverhalbaffe.monkeyapi.domain.Species;
import com.nachtaktiverhalbaffe.monkeyapi.domain.dto.SpeciesDto;
import com.nachtaktiverhalbaffe.monkeyapi.mapper.Mapper;
import com.nachtaktiverhalbaffe.monkeyapi.services.SpeciesService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(path = "api/v1/species")
public class SpeciesController {

    private SpeciesService speciesService;
    private Mapper<Species, SpeciesDto> speciesMapper;

    public SpeciesController(SpeciesService speciesService, Mapper<Species, SpeciesDto> speciesMapper) {
        this.speciesService = speciesService;
        this.speciesMapper = speciesMapper;
    }

    @GetMapping
    public ResponseEntity<Page<SpeciesDto>> getAllSpecies(Pageable pageable) {
        Page<Species> species = this.speciesService.getAll(pageable);
        return new ResponseEntity<>(species.map(speciesMapper::mapTo), HttpStatus.OK);
    }

    @GetMapping(path = { "{name}" })
    public ResponseEntity<SpeciesDto> getSpeciesByName(@PathVariable("name") String name) {
        Optional<Species> species = this.speciesService.getByName(name);

        if (species.isPresent()) {
            return new ResponseEntity<>(this.speciesMapper.mapTo(species.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<SpeciesDto> addSpecies(@RequestBody SpeciesDto speciesDto) {
        if (!this.speciesService.exists(speciesDto.getName())) {
            Species savedSpecies = this.speciesService.createSpecies(this.speciesMapper.mapFrom(speciesDto));
            return new ResponseEntity<>(speciesMapper.mapTo(savedSpecies), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public ResponseEntity<SpeciesDto> updateSpecies(@RequestBody SpeciesDto speciesDto) {
        if (!this.speciesService.exists(speciesDto.getName())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Species updatedSpecies = this.speciesService.update(this.speciesMapper.mapFrom(speciesDto));
        return new ResponseEntity<>(this.speciesMapper.mapTo(updatedSpecies), HttpStatus.OK);
    }

    @DeleteMapping(path = "{name}")
    public ResponseEntity<Void> deleteSpeciesByName(@PathVariable("name") String name) {
        this.speciesService.deleteByName(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteSpecies(@RequestBody SpeciesDto speciesDto) {
        this.speciesService.delete(this.speciesMapper.mapFrom(speciesDto));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
