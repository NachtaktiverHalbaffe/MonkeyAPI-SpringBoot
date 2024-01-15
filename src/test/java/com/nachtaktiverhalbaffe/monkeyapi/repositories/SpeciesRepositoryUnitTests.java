package com.nachtaktiverhalbaffe.monkeyapi.repositories;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.nachtaktiverhalbaffe.monkeyapi.TestData;
import com.nachtaktiverhalbaffe.monkeyapi.domain.Species;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SpeciesRepositoryUnitTests {

        @Autowired
        private SpeciesRepository repositoryUnderTest;

        private Species testDataSpecies = TestData.createTestSpecies();

        @AfterEach
        private void clearDb() {
                repositoryUnderTest.deleteAll();
        }

        @Test
        public void testThatEntitiesCanBeSaved() {
                Species result = repositoryUnderTest.save(testDataSpecies);

                Assertions.assertThat(result)
                                .isNotNull()
                                .isEqualTo(testDataSpecies);
        }

        @Test
        public void testThatSpeciesCanBeFoundByName() {
                repositoryUnderTest.save(testDataSpecies);
                Optional<Species> result = repositoryUnderTest.findByName(testDataSpecies.getName());

                Assertions.assertThat(result)
                                .isPresent()
                                .get().isEqualTo(testDataSpecies);
        }

        @Test
        public void testThatAllSpeciesCanBeLoaded() {
                repositoryUnderTest.save(testDataSpecies);

                List<Species> result = StreamSupport.stream(repositoryUnderTest.findAll().spliterator(), false)
                                .collect(Collectors.toList());

                Assertions.assertThat(result)
                                .isNotNull()
                                .hasSize(1)
                                .first().isEqualTo(testDataSpecies);
        }

        @Test
        public void testThatSpeciesCanBeDeleted() {
                repositoryUnderTest.save(testDataSpecies);
                List<Species> initialCheck = StreamSupport.stream(repositoryUnderTest.findAll().spliterator(), false)
                                .collect(Collectors.toList());
                Assertions.assertThat(initialCheck)
                                .isNotNull()
                                .hasSize(1)
                                .first().isEqualTo(testDataSpecies);

                repositoryUnderTest.delete(testDataSpecies);

                List<Species> result = StreamSupport.stream(repositoryUnderTest.findAll().spliterator(), false)
                                .collect(Collectors.toList());
                Assertions.assertThat(result)
                                .isNotNull()
                                .hasSize(0);
        }

        @Test
        public void testThatSpeciesCanBeDeletedByName() {
                repositoryUnderTest.save(testDataSpecies);
                List<Species> initialCheck = StreamSupport.stream(repositoryUnderTest.findAll().spliterator(), false)
                                .collect(Collectors.toList());
                Assertions.assertThat(initialCheck)
                                .isNotNull()
                                .hasSize(1)
                                .first().isEqualTo(testDataSpecies);

                repositoryUnderTest.deleteByName(testDataSpecies.getName());

                List<Species> result = StreamSupport.stream(repositoryUnderTest.findAll().spliterator(), false)
                                .collect(Collectors.toList());
                Assertions.assertThat(result)
                                .isNotNull()
                                .hasSize(0);
        }

        @Test
        public void testThatIfExistsByNameReturnsTrue() {
                repositoryUnderTest.save(testDataSpecies);

                boolean result = repositoryUnderTest.existsByName(testDataSpecies.getName());

                Assertions.assertThat(result).isNotNull().isTrue();
        }
}
