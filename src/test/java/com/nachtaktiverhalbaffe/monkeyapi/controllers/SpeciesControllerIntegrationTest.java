package com.nachtaktiverhalbaffe.monkeyapi.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nachtaktiverhalbaffe.monkeyapi.TestDataSpecies;
import com.nachtaktiverhalbaffe.monkeyapi.domain.Species;
import com.nachtaktiverhalbaffe.monkeyapi.domain.dto.SpeciesDto;
import com.nachtaktiverhalbaffe.monkeyapi.mapper.Mapper;
import com.nachtaktiverhalbaffe.monkeyapi.services.SpeciesService;

@WebMvcTest(controllers = SpeciesController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class SpeciesControllerIntegrationTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private SpeciesService mockService;

        @MockBean
        private Mapper<Species, SpeciesDto> mockMapper;

        @Autowired
        private ObjectMapper objectMapper;

        private Species testSpecies;
        private SpeciesDto testSpeciesDto;

        @BeforeEach
        public void init() {
                testSpecies = TestDataSpecies.createTestSpecies();
                testSpeciesDto = TestDataSpecies.createTestSpeciesDto();
                Mockito.when(mockMapper.mapTo(Mockito.any(Species.class))).thenReturn(testSpeciesDto);
                Mockito.when(mockMapper.mapFrom(Mockito.any(SpeciesDto.class))).thenReturn(testSpecies);
        }

        @Test
        public void testGetAllSpecies() throws Exception {
                Page<Species> speciesPage = Mockito.mock(Page.class);
                Mockito.when(mockService.getAll(Mockito.any(Pageable.class))).thenReturn(speciesPage);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/species").contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void testGetSpeciesByName() throws Exception {
                Mockito.when(mockService.getByName(Mockito.anyString())).thenReturn(Optional.of(testSpecies));

                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/species/" + testSpecies.getName())
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(testSpecies.getName()));
        }

        @Test
        public void testGetSpeciesByNameFail() throws Exception {
                Mockito.when(mockService.getByName(Mockito.anyString())).thenReturn(Optional.empty());

                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/species/" + testSpecies.getName())
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }

        @Test
        public void testAddSpecies() throws Exception {
                String jsonPayload = objectMapper.writeValueAsString(testSpeciesDto);
                Mockito.when(mockService.createSpecies(Mockito.any(Species.class))).thenReturn(testSpecies);
                Mockito.when(mockService.exists(Mockito.any(String.class))).thenReturn(false);

                mockMvc.perform(
                                MockMvcRequestBuilders.post("/api/v1/species")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(jsonPayload))
                                .andExpect(MockMvcResultMatchers.status().isCreated())
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(testSpecies.getName()));
        }

        @Test
        public void testAddSpeciesFail() throws Exception {
                String jsonPayload = objectMapper.writeValueAsString(testSpecies);
                Mockito.when(mockService.createSpecies(Mockito.any(Species.class))).thenReturn(testSpecies);
                Mockito.when(mockService.exists(Mockito.any(String.class))).thenReturn(true);

                mockMvc.perform(
                                MockMvcRequestBuilders.post("/api/v1/species")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(jsonPayload))
                                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }

        @Test
        public void testDeleteSpecies() throws Exception {
                String jsonPayload = objectMapper.writeValueAsString(testSpecies);
                Mockito.doNothing().when(mockService).delete(testSpecies);

                mockMvc.perform(
                                MockMvcRequestBuilders.delete("/api/v1/species")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(jsonPayload))
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

                mockMvc.perform(
                                MockMvcRequestBuilders.delete("/api/v1/species")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(jsonPayload))
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        }

        @Test
        public void testDeleteSpeciesByName() throws Exception {
                String jsonPayload = objectMapper.writeValueAsString(testSpecies);
                Mockito.doNothing().when(mockService).deleteByName(testSpecies.getName());

                mockMvc.perform(
                                MockMvcRequestBuilders.delete("/api/v1/species/" + testSpecies.getName())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(jsonPayload))
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

                mockMvc.perform(
                                MockMvcRequestBuilders.delete("/api/v1/species/" + testSpecies.getName())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(jsonPayload))
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        }

}
