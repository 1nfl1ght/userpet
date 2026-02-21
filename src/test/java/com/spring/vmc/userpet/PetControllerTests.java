package com.spring.vmc.userpet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.vmc.userpet.model.dto.PetDto;
import com.spring.vmc.userpet.model.entity.Pet;
import com.spring.vmc.userpet.service.PetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PetControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetService petService;

    private final ObjectMapper mapper = new ObjectMapper();

    private PetDto testPet;

    @BeforeEach
    void setUp() {
        testPet = new PetDto(1L, "Dog", 1L);
    }

    @Test
    void successCreatePet() throws Exception {
        PetDto pet = new PetDto(
                1L,
                "Cat",
                1L
        );

        PetDto savedPet = new PetDto(
                1L,
                "Cat",
                1L
        );

        when(petService.createPet(any(PetDto.class))).thenReturn(savedPet);

        String createdPetJson = mockMvc.perform(post("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(pet)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PetDto petResponse = mapper.readValue(createdPetJson, PetDto.class);

        Assertions.assertEquals(pet.getName(), petResponse.getName());
        Assertions.assertNotNull(petResponse.getId());
    }

    @Test
    void successGetPet() throws Exception {
        when(petService.getPetById(1L)).thenReturn(testPet);

        mockMvc.perform(get("/pets/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dog"))
                .andExpect(jsonPath("$.userId").value(1))
                .andDo(print());
    }

    @Test
    void successUpdatePet() throws Exception {
        PetDto updatedPet = new PetDto(
                1L,
                "Cat",
                1L
        );

        when(petService.updatePet(eq(1L), any(PetDto.class))).thenReturn(updatedPet);

        mockMvc.perform(put("/pets/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatedPet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cat"))
                .andExpect(jsonPath("$.userId").value(1L))
                .andDo(print());
    }

    @Test
    void successDeletePet() throws Exception {
        doNothing().when(petService).deletePetById(1L);

        mockMvc.perform(delete("/pets/{id}", 1L))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""))
                .andDo(print());
    }

    @Test
    void unsuccessCreatePetWithEmptyName() throws Exception {
        PetDto pet = new PetDto(
                null,
                null,
                1L
        );

        String userJson = mapper.writeValueAsString(pet);

        mockMvc.perform(post("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists())
                .andDo(print());

    }

}
