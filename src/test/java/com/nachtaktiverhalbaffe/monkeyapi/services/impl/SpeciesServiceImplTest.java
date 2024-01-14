package com.nachtaktiverhalbaffe.monkeyapi.services.impl;

import static org.mockito.Answers.values;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.nachtaktiverhalbaffe.monkeyapi.TestDataSpecies;
import com.nachtaktiverhalbaffe.monkeyapi.domain.Species;
import com.nachtaktiverhalbaffe.monkeyapi.repositories.SpeciesRepository;

@ExtendWith(MockitoExtension.class)
public class SpeciesServiceImplTest {

    @Mock
    private SpeciesRepository mockRepository;

    @InjectMocks
    private SpeciesServiceImpl serviceUnderTest;

    private Species testSpecies = TestDataSpecies.createTestSpecies();

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
    public void testGetByNameFails() {
        Optional<Species> speciesOptional = Mockito.mock(Optional.class);
        Mockito.when(speciesOptional.isPresent()).thenReturn(false);
        Mockito.when(mockRepository.findByName(Mockito.any(String.class))).thenReturn(speciesOptional);

        Optional<Species> result = serviceUnderTest.getByName("Cheetah");

        Assertions.assertThat(result).isNotPresent();

    }

}
