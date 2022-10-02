package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserController repository;
    private User user;

    @BeforeEach
    public void beforeEach() {
        user = new User();
        user.setId(1L);
    }

    @Test
    public void createUserWithFailLoginAndReturnBadRequest() throws Exception {
        user.setLogin(" ");
        user.setEmail("test@test.com");
        user.setBirthday(LocalDate.of(1990, 6, 6));
        Mockito.when(repository.create(Mockito.any())).thenReturn(user);
        mockMvc.perform(
                post("/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createUserWithFailEmailAndReturnBadRequest() throws Exception {
        user.setLogin("test");
        user.setEmail("testtest.com");
        user.setBirthday(LocalDate.of(1990, 6, 6));
        Mockito.when(repository.create(Mockito.any())).thenReturn(user);
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createUserWithFailBirthdayAndReturnBadRequest() throws Exception {
        user.setLogin("test");
        user.setEmail("test@test.com");
        user.setBirthday(LocalDate.of(2028, 6, 6));
        Mockito.when(repository.create(Mockito.any())).thenReturn(user);
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateUserWithFailIdAndReturnBadRequest() throws Exception {
        user.setId(10L);
        user.setEmail("test@test.com");
        user.setBirthday(LocalDate.of(1990, 6, 6));
        mockMvc.perform(
                        put("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }
}
