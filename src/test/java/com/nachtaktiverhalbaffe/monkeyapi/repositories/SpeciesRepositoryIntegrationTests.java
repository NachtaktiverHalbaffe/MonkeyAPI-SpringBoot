package com.nachtaktiverhalbaffe.monkeyapi.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.contains;

import com.nachtaktiverhalbaffe.monkeyapi.TestDataSpecies;
import com.nachtaktiverhalbaffe.monkeyapi.domain.Species;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SpeciesRepositoryIntegrationTests {

    private SpeciesRepository underTest;

    @Autowired
    public SpeciesRepositoryIntegrationTests(SpeciesRepository speciesRepository) {
        this.underTest = speciesRepository;
    }

    @Test
    public void speciesCanBeCreated() {
        Species species = TestDataSpecies.createTestSpecies();
        underTest.save(species);
        Optional<Species> result = underTest.findById(species.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(species);
    }

    @Test
    public void speciesCanBeFoundByName() {
        Species species = TestDataSpecies.createTestSpecies();
        underTest.save(species);
        Optional<Species> result = underTest.findByName(species.getName());
        assertThat(result).isPresent();
        assertThat(result).isEqualTo(species);
    }

}
