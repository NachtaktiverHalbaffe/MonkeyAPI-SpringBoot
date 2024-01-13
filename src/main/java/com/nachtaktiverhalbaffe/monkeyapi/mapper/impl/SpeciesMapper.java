package com.nachtaktiverhalbaffe.monkeyapi.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.nachtaktiverhalbaffe.monkeyapi.domain.Species;
import com.nachtaktiverhalbaffe.monkeyapi.domain.dto.SpeciesDto;
import com.nachtaktiverhalbaffe.monkeyapi.mapper.Mapper;

@Component
public class SpeciesMapper implements Mapper<Species, SpeciesDto> {

    private ModelMapper modelMapper;

    private SpeciesMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public SpeciesDto mapTo(Species species) {
        return this.modelMapper.map(species, SpeciesDto.class);
    }

    @Override
    public Species mapFrom(SpeciesDto speciesDto) {
        return this.modelMapper.map(speciesDto, Species.class);
    }

    @Override
    public List<SpeciesDto> mapAllTo(List<Species> species) {
        List<SpeciesDto> dtos = new ArrayList<>();

        species.forEach(element -> dtos.add(mapTo(element)));

        return dtos;
    }

    @Override
    public List<Species> mapFromAll(List<SpeciesDto> dtos) {
        List<Species> species = new ArrayList<>();

        dtos.forEach(element -> species.add(mapFrom(element)));

        return species;
    }

}
