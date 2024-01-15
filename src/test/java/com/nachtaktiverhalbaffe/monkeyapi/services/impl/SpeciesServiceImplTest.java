package com.nachtaktiverhalbaffe.monkeyapi.services.impl;

import static org.mockito.Answers.values;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.nachtaktiverhalbaffe.monkeyapi.TestData;
import com.nachtaktiverhalbaffe.monkeyapi.config.ApiKeys;
import com.nachtaktiverhalbaffe.monkeyapi.domain.Species;
import com.nachtaktiverhalbaffe.monkeyapi.repositories.SpeciesRepository;

@ExtendWith(MockitoExtension.class)
public class SpeciesServiceImplTest {

    @Mock
    private SpeciesRepository mockRepository;

    @Mock
    private ApiKeys mockAnimalsAPIConfig = new ApiKeys();

    @InjectMocks
    private SpeciesServiceImpl serviceUnderTest;

    private Species testSpecies = TestData.createTestSpecies();

    @Test
    public void testCreateSpecies() {
        when(mockRepository.save(Mockito.any(Species.class))).thenReturn(testSpecies);

        Species result = serviceUnderTest.createSpecies(testSpecies);

        Assertions.assertThat(result)
                .isNotNull()
                .isEqualTo(testSpecies);
    }

    @Test
    public void testGetAllSpeciesPagable() {
        Page<Species> speciesPage = Mockito.mock(Page.class);
        when(mockRepository.findAll(Mockito.any(Pageable.class))).thenReturn(speciesPage);

        Page<Species> result = serviceUnderTest.getAll(PageRequest.of(1, 10));

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void testGetAllSpecies() {
        Iterable<Species> speciesIterable = Mockito.mock(Iterable.class);
        Species[] speciesArray = { testSpecies };
        when(mockRepository.findAll()).thenReturn(speciesIterable);
        Mockito.when(speciesIterable.spliterator()).thenReturn(Arrays.spliterator(speciesArray));

        List<Species> result = serviceUnderTest.getAll();

        Assertions.assertThat(result)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    public void testGetByName() {
        Optional<Species> speciesOptional = Mockito.mock(Optional.class);
        Mockito.when(speciesOptional.get()).thenReturn(testSpecies);
        Mockito.when(speciesOptional.isPresent()).thenReturn(true);
        Mockito.when(mockRepository.findByName(Mockito.any(String.class))).thenReturn(speciesOptional);

        Optional<Species> result = serviceUnderTest.getByName("Cheetah");

        Assertions.assertThat(result).isPresent().get().isEqualTo(testSpecies);
    }

    @Test
    public void testGetByNameWithAPIFetching() {
        Optional<Species> speciesOptional = Mockito.mock(Optional.class);
        Mockito.when(speciesOptional.isPresent()).thenReturn(false);
        Mockito.when(mockRepository.findByName(Mockito.any(String.class))).thenReturn(speciesOptional);
        Mockito.when(mockAnimalsAPIConfig.getAnimalsApi()).thenReturn("9oNX8SJL0T/3hvfIxr1Q7Q==S7hYKbxkRWY0txqs");

        Optional<Species> result = serviceUnderTest.getByName("Cheetah");

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().getTaxonomy()).isNotEmpty();
        Assertions.assertThat(result.get().getLocations()).isNotEmpty();
        Assertions.assertThat(result.get().getCharacteristics()).isNotEmpty();
        Assertions.assertThat(result.get().getName()).isEqualTo(testSpecies.getName());
    }

    @Test
    public void testGetByNameFails() {
        Optional<Species> speciesOptional = Mockito.mock(Optional.class);
        Mockito.when(speciesOptional.isPresent()).thenReturn(false);
        Mockito.when(mockRepository.findByName(Mockito.any(String.class))).thenReturn(speciesOptional);
        Mockito.when(mockAnimalsAPIConfig.getAnimalsApi()).thenReturn("9oNX8SJL0T/3hvfIxr1Q7Q==S7hYKbxkRWY0txqs");

        Optional<Species> result = serviceUnderTest.getByName("asfdasfasdf");

        Assertions.assertThat(result).isNotPresent();

    }

}
