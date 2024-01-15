package com.nachtaktiverhalbaffe.monkeyapi.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import java.util.Optional;

import org.hibernate.cfg.Environment;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nachtaktiverhalbaffe.monkeyapi.TestData;
import com.nachtaktiverhalbaffe.monkeyapi.domain.Monkey;
import com.nachtaktiverhalbaffe.monkeyapi.domain.dto.MonkeyDto;
import com.nachtaktiverhalbaffe.monkeyapi.services.MonkeyService;

@WebMvcTest(controllers = MonkeyController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class MonkeyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MonkeyService mockService;

    @MockBean
    private Environment mockEnvironment;

    @Autowired
    private ObjectMapper objectMapper;

    private Monkey testMonkey;
    private MonkeyDto testMonkeyDto;

    @BeforeEach
    public void setup() {
        testMonkey = TestData.createTestMonkey();
        testMonkeyDto = TestData.createTestMonkeyDto();
    }

    @Test
    public void testGetAllSpecies() throws Exception {
        Page<Monkey> monkeyPage = Mockito.mock(Page.class);
        Mockito.when(mockService.getAll(Mockito.any(Pageable.class))).thenReturn(monkeyPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/monkeys").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetMonkeyById() throws Exception {
        Mockito.when(mockService.get(Mockito.eq(2L))).thenReturn(Optional.of(testMonkey));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/monkeys/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(testMonkey.getName()));
    }

    @Test
    public void testGetMonkeyByIdFails() throws Exception {
        Mockito.when(mockService.get(Mockito.anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/monkeys/5").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void testGetMonkeyByName() throws Exception {
        Mockito.when(mockService.get(Mockito.eq(testMonkey.getName()))).thenReturn(Optional.of(testMonkey));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/monkeys/by_name/" + testMonkey.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(testMonkey.getName()));
    }

    @Test
    public void testGetMonkeyByNameFails() throws Exception {
        Mockito.when(mockService.get(Mockito.anyString())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/monkeys/by_name/adf")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void testCreateNewMonkey() throws Exception {
        MockMultipartFile file = new MockMultipartFile("data", "dummy.csv",
                "text/plain", "Some dataset...".getBytes());
        String jsonPayLoad = objectMapper.writeValueAsString(testMonkeyDto);
        Mockito.when(mockService.create(any(Optional.class), any(Monkey.class), any(Optional.class)))
                .thenReturn(Optional.of(testMonkey));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/monkeys")
                .param("body", jsonPayLoad)
                .param("image", file.toString()))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(testMonkey.getName()));
    }

    @Test
    public void testCreateNewMonkeyFailsWithMalformedJson() throws Exception {
        MockMultipartFile file = new MockMultipartFile("data", "dummy.csv",
                "text/plain", "Some dataset...".getBytes());
        Mockito.when(mockService.create(any(Optional.class), any(Monkey.class), any(Optional.class)))
                .thenReturn(Optional.of(testMonkey));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/monkeys")
                .param("body", "sfasdf")
                .param("image", file.toString()))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void testCreateNewMonkeyFails() throws Exception {
        Mockito.when(mockService.create(any(Optional.class), any(Monkey.class), any(Optional.class)))
                .thenReturn(Optional.of(testMonkey));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/monkeys"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void testUpdateMonkey() throws Exception {
        MockMultipartFile file = new MockMultipartFile("data", "dummy.csv",
                "text/plain", "Some dataset...".getBytes());
        MonkeyDto updateData = testMonkeyDto;
        Monkey expectedMonkey = testMonkey;
        testMonkeyDto.setName("Cesar");
        expectedMonkey.setName("Cesar");
        String jsonPayLoad = objectMapper.writeValueAsString(updateData);
        Mockito.when(mockService.exists(eq(testMonkeyDto.getId()))).thenReturn(true);
        Mockito.when(mockService.exists(anyString())).thenReturn(true);
        Mockito.when(
                mockService.update(any(Optional.class), any(Monkey.class)))
                .thenReturn(Optional.of(testMonkey));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/monkeys")
                .param("body", jsonPayLoad)
                .param("image", file.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expectedMonkey.getName()));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/monkeys/" + testMonkeyDto.getId())
                .param("body", jsonPayLoad)
                .param("image", file.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expectedMonkey.getName()));
    }

    @Test
    public void testUpdateMonkeyWithoutJson() throws Exception {
        MockMultipartFile file = new MockMultipartFile("data", "dummy.csv",
                "text/plain", "Some dataset...".getBytes());

        Mockito.when(mockService.exists(eq(testMonkeyDto.getId()))).thenReturn(true);
        Mockito.when(mockService.exists(anyString())).thenReturn(true);
        Mockito.when(
                mockService.update(any(Optional.class), any(Monkey.class)))
                .thenReturn(Optional.of(testMonkey));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/monkeys")
                .param("image", file.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/monkeys/" + testMonkeyDto.getId())
                .param("image", file.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(testMonkey.getName()));
    }

    @Test
    public void testUpdateMonkeyWithoutImage() throws Exception {
        MonkeyDto updateData = testMonkeyDto;
        Monkey expectedMonkey = testMonkey;
        testMonkeyDto.setName("Cesar");
        expectedMonkey.setName("Cesar");
        String jsonPayLoad = objectMapper.writeValueAsString(updateData);
        Mockito.when(mockService.exists(eq(testMonkeyDto.getId()))).thenReturn(true);
        Mockito.when(mockService.exists(anyString())).thenReturn(true);
        Mockito.when(
                mockService.update(any(Optional.class), any(Monkey.class)))
                .thenReturn(Optional.of(testMonkey));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/monkeys")
                .param("body", jsonPayLoad))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expectedMonkey.getName()));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/monkeys/" + testMonkeyDto.getId())
                .param("body", jsonPayLoad))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expectedMonkey.getName()));
    }

}
