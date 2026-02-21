package com.spring.vmc.userpet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.vmc.userpet.model.dto.UserDto;
import com.spring.vmc.userpet.model.entity.User;
import com.spring.vmc.userpet.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private final ObjectMapper mapper = new ObjectMapper();

    private UserDto testUser;

    @BeforeEach
    void setUp() {
        testUser = new UserDto(1L, "Pavel", "test@email.com", 25);
    }

    @Test
    void successCreateUser() throws Exception {
        UserDto user = new UserDto(
                null,
                "Max",
                "email@email.ru",
                32
        );

        UserDto savedUser = new UserDto(
                1L,
                "Max",
                "email@email.ru",
                32
        );

        when(userService.createUser(any(UserDto.class))).thenReturn(savedUser);

        String createdUserJson = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto userResponse = mapper.readValue(createdUserJson, UserDto.class);

        Assertions.assertEquals(user.getName(), userResponse.getName());
        Assertions.assertNotNull(userResponse.getId());
    }

    @Test
    void successGetUser() throws Exception {
        when(userService.getUserById(1L)).thenReturn(testUser);

        mockMvc.perform(get("/users/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pavel"))
                .andExpect(jsonPath("$.email").value("test@email.com"))
                .andExpect(jsonPath("$.age").value(25))
                .andDo(print());
    }

    @Test
    void successUpdateUser() throws Exception {
        UserDto updatedUser = new UserDto(
                1L,
                "Pasha",
                "test@mail.ru",
                25
        );

        when(userService.updateUser(eq(1L), any(UserDto.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pasha"))
                .andExpect(jsonPath("$.email").value("test@mail.ru"))
                .andDo(print());
    }

    @Test
    void successDeleteUser() throws Exception {
        doNothing().when(userService).delete(1L);

        mockMvc.perform(delete("/users/{id}", 1L))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""))
                .andDo(print());
    }

    @Test
    void unsuccessCreateUserWithEmptyName() throws Exception {
        UserDto user = new UserDto(
                null,
                null,
                "email@mail.ru",
                23
        );

        String userJson = mapper.writeValueAsString(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists())
                .andDo(print());

    }

}
