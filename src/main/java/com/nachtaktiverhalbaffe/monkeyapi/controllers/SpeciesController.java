package com.nachtaktiverhalbaffe.monkeyapi.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.nachtaktiverhalbaffe.monkeyapi.domain.Species;
import com.nachtaktiverhalbaffe.monkeyapi.domain.dto.SpeciesDto;
import com.nachtaktiverhalbaffe.monkeyapi.mapper.Mapper;
import com.nachtaktiverhalbaffe.monkeyapi.services.SpeciesService;

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
    public List<SpeciesDto> getAllSpecies() {
        return this.speciesMapper.mapAllTo(this.speciesService.getAllSpecies());
    }

    @GetMapping(path = { "{id}" })
    public SpeciesDto getSpeciesById(@PathVariable("id") Long id) {
        return this.speciesMapper.mapTo(this.speciesService.getSpeciesById(id).get());
    }

    // @GetMapping(path = { "{name}" })
    // public SpeciesDto getSpeciesByName(@PathVariable("name") String name) {
    // return
    // this.speciesMapper.mapTo(this.speciesService.getSpeciesbyName(name).get());
    // }

    @PostMapping
    public void addSpecies(@RequestBody SpeciesDto speciesDto) {
        this.speciesService.createSpecies(this.speciesMapper.mapFrom(speciesDto));
    }

    @DeleteMapping(path = "{id}")
    public void deleteSpeciesById(@PathVariable("id") Long id) {
        this.speciesService.deleteSpeciesById(id);
    }

    @DeleteMapping(path = "{name}")
    public void deleteSpeciesByName(@PathVariable("name") String name) {
        this.speciesService.deleteSpeciesByName(name);
    }

    @DeleteMapping
    public void deleteSpecies(@RequestBody SpeciesDto speciesDto) {
        this.speciesService.deleteSpecies(this.speciesMapper.mapFrom(speciesDto));
    }

    @PutMapping(path = "{id}")
    public void updateSpecies(@RequestBody SpeciesDto speciesDto) {
        this.speciesService.updateSpecies(this.speciesMapper.mapFrom(speciesDto));
    }
}
