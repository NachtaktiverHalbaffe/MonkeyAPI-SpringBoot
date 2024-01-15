package com.nachtaktiverhalbaffe.monkeyapi.services.impl;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import com.nachtaktiverhalbaffe.monkeyapi.TestData;
import com.nachtaktiverhalbaffe.monkeyapi.domain.ImageData;
import com.nachtaktiverhalbaffe.monkeyapi.domain.Monkey;
import com.nachtaktiverhalbaffe.monkeyapi.domain.Species;
import com.nachtaktiverhalbaffe.monkeyapi.domain.dto.MonkeyDto;
import com.nachtaktiverhalbaffe.monkeyapi.repositories.MonkeyRepository;
import com.nachtaktiverhalbaffe.monkeyapi.services.MonkeyService;
import com.nachtaktiverhalbaffe.monkeyapi.services.SpeciesService;

@ExtendWith(MockitoExtension.class)
public class MonkeyServiceImplTest {

        @Mock
        private MonkeyRepository mockRepository;

        @Mock
        private SpeciesService mockSpeciesService;

        @Mock
        private ImageDataService mockImagaDataService;

        @InjectMocks
        private MonkeyServiceImpl serviceUnderTest;

        private Monkey testMonkeyFromDto;
        private Monkey testMonkey;

        @BeforeEach
        public void setup() {
                testMonkeyFromDto = TestData.createTestMonkeyDto().mapToEntity();
                testMonkey = TestData.createTestMonkey();
        }

        @Test
        public void testThatMonkeysAreCreatedWithImageAndWithoutSpeciesContent() {
                Optional<MultipartFile> mockImage = Mockito.mock(Optional.class);
                Mockito.when(mockImage.isPresent()).thenReturn(true);
                Mockito.when(mockRepository.existsById(Mockito.any())).thenReturn(false);
                Mockito.when(mockRepository.existsByName(Mockito.any())).thenReturn(false);
                Mockito.when(mockRepository.save(Mockito.any(Monkey.class))).thenReturn(TestData.createTestMonkey());
                Mockito.when(mockImagaDataService.storeFile(Mockito.any()))
                                .thenReturn(Optional.of(TestData.createTestImageData()));
                Mockito.when(mockSpeciesService.getByName(Mockito.anyString()))
                                .thenReturn(Optional.of(TestData.createTestSpecies()));
                testMonkeyFromDto.setSpecies(null);

                Optional<Monkey> result = serviceUnderTest.create(mockImage, testMonkeyFromDto,
                                Optional.of(testMonkeyFromDto.getName()));

                Assertions.assertThat(result).isPresent().get().isEqualTo(testMonkey);
        }

        @Test
        public void testThatMonkeysAreCreatedWithImageAndWithSpeciesContent() {
                Optional<MultipartFile> mockImage = Mockito.mock(Optional.class);
                Mockito.when(mockImage.isPresent()).thenReturn(true);
                Mockito.when(mockRepository.existsById(Mockito.any())).thenReturn(false);
                Mockito.when(mockRepository.existsByName(Mockito.any())).thenReturn(false);
                Mockito.when(mockRepository.save(Mockito.any(Monkey.class))).thenReturn(TestData.createTestMonkey());
                Mockito.when(mockImagaDataService.storeFile(Mockito.any()))
                                .thenReturn(Optional.of(TestData.createTestImageData()));

                Optional<Monkey> result = serviceUnderTest.create(mockImage, testMonkeyFromDto,
                                Optional.of(testMonkeyFromDto.getName()));

                Assertions.assertThat(result).isPresent().get().isEqualTo(testMonkey);
        }

        @Test
        public void testThatMonkeysAreCreatedWithoutImageAndWithSpeciesName() {
                Optional<MultipartFile> mockImage = Mockito.mock(Optional.class);
                Mockito.when(mockImage.isPresent()).thenReturn(false);
                Mockito.when(mockRepository.existsById(Mockito.any())).thenReturn(false);
                Mockito.when(mockRepository.existsByName(Mockito.any())).thenReturn(false);
                Mockito.when(mockRepository.save(Mockito.any(Monkey.class))).thenReturn(TestData.createTestMonkey());
                testMonkeyFromDto.setSpecies(null);

                Optional<Monkey> result = serviceUnderTest.create(mockImage, testMonkeyFromDto,
                                Optional.of(testMonkeyFromDto.getName()));

                Assertions.assertThat(result).isPresent().get().isEqualTo(testMonkey);
        }

        @Test
        public void testThatMonkeysAreCreatedWithoutImageAndWithoutAnySpecies() {
                Optional<MultipartFile> mockImage = Mockito.mock(Optional.class);
                Mockito.when(mockImage.isPresent()).thenReturn(false);
                Mockito.when(mockRepository.existsById(Mockito.any())).thenReturn(false);
                Mockito.when(mockRepository.existsByName(Mockito.any())).thenReturn(false);
                Mockito.when(mockRepository.save(Mockito.any(Monkey.class))).thenReturn(TestData.createTestMonkey());
                testMonkeyFromDto.setSpecies(null);

                Optional<Monkey> result = serviceUnderTest.create(mockImage, testMonkeyFromDto,
                                Optional.empty());

                Assertions.assertThat(result).isPresent().get().isEqualTo(testMonkey);
        }

        @Test
        public void testThatAllMonkeysAreReturnedAsList() {
                Iterable<Monkey> monkeysIterable = Mockito.mock(Iterable.class);
                Monkey[] monkeyArray = { testMonkey };
                when(mockRepository.findAll()).thenReturn(monkeysIterable);
                Mockito.when(monkeysIterable.spliterator()).thenReturn(Arrays.spliterator(monkeyArray));

                List<Monkey> result = serviceUnderTest.getAll();

                Assertions.assertThat(result)
                                .isNotNull()
                                .hasSize(1);
        }

        @Test
        public void testThatMonkeyIsUpdated() {
                Monkey updateData = new MonkeyDto().mapToEntity();
                Monkey updatedMonkey = testMonkey;
                updateData.setId(testMonkey.getId());
                updateData.setName("Cesar");
                updatedMonkey.setName("Cesar");
                updateData.setAttack(234);
                updatedMonkey.setAttack(234);
                Mockito.when(mockRepository.findById(anyLong())).thenReturn(Optional.of(testMonkey));
                Mockito.when(mockRepository.save(Mockito.any(Monkey.class))).thenReturn(updatedMonkey);

                Optional<Monkey> result = serviceUnderTest.update(Optional.empty(), updateData);

                Assertions.assertThat(result).isPresent().get().isEqualTo(updatedMonkey);
        }

        @Test
        public void testThatMonkeyDoesntUpdateWithDefaultData() {

                Optional<Monkey> result = serviceUnderTest.update(Optional.empty(), new Monkey());

                Assertions.assertThat(result).isNotPresent();
        }

}
