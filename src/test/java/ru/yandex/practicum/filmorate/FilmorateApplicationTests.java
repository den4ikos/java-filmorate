package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
class FilmorateApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    String validFilm;
    String inValidFilm;
    String validUser;
    String inValidUser;

    @BeforeEach
    public void beforeEach() {
        validFilm = "{\"id\":null,\"name\":\"Film Name\",\"description\":\"Film description\",\"releaseDate\":\"2015-03-01\",\"duration\":100}";
        validUser = "{\"id\":null,\"email\":\"test@yandex.ru\",\"login\":\"TestLogin\",\"name\":\"Test Name\",\"birthday\":\"1990-03-01\"}";
    }

    @SneakyThrows
    @Test
    void contextLoads() {
        mockMvc.perform(get("/films")
                .contentType(MediaType.APPLICATION_JSON)).andDo(h->
                System.out.println(h.getResponse())
        );

        mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON)).andDo(h->
                System.out.println(h.getResponse())
        );

    }

    @SneakyThrows
    @Test
    public void createFilmWithEmptyTitleAndReturn400Status() {
        inValidFilm = "{\"id\":null,\"name\":\"\",\"description\":\"Film description\",\"releaseDate\":\"2015-03-01\",\"duration\":100}";
        mockMvc.perform(post("/films").contentType(MediaType.APPLICATION_JSON).content(inValidFilm))
                .andExpect(status().is4xxClientError())
                .andDo(h -> System.out.println(h.getResponse()));
    }

    @SneakyThrows
    @Test
    public void createFilmWithFailDescriptionAndReturn400Status() {
        inValidFilm = "{\"id\":null,\"name\":\"Test name\",\"description\":\"Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc, quis\",\"releaseDate\":\"2015-03-01\",\"duration\":100}";
        mockMvc.perform(post("/films").contentType(MediaType.APPLICATION_JSON).content(inValidFilm))
                .andExpect(status().is4xxClientError())
                .andDo(h -> System.out.println(h.getResponse()));
    }

    @SneakyThrows
    @Test
    public void createFilmWithFailDurationAndReturnBadRequest() {
        inValidFilm = "{\"id\":null,\"name\":\"Test name\",\"description\":\"Lorem ipsum dolor sit amet\",\"releaseDate\":\"2015-03-01\",\"duration\":-1}";
        mockMvc.perform(post("/films").contentType(MediaType.APPLICATION_JSON).content(inValidFilm))
                .andExpect(status().isBadRequest())
                .andDo(h -> System.out.println(h.getResponse()));
    }

    @SneakyThrows
    @Test
    public void updateFilmWithFailIdAndReturn400Status() {
        mockMvc.perform(put("/films").contentType(MediaType.APPLICATION_JSON).content(validFilm))
                .andExpect(status().is4xxClientError())
                .andDo(h -> System.out.println(h.getResponse()));
    }

    @SneakyThrows
    @Test
    public void createUserWithFailLoginAndReturnBadRequest() {
        inValidUser = "{\"id\":null,\"email\":\"test@yandex.ru\",\"login\":\" \",\"name\":\"Test Name\",\"birthday\":\"1990-03-01\"}";;
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(inValidUser))
                .andExpect(status().isBadRequest())
                .andDo(h -> System.out.println(h.getResponse()));
    }

    @SneakyThrows
    @Test
    public void createUserWithFailEmailAndReturnBadRequest() {
        inValidUser = "{\"id\":null,\"email\":\"testyandex.ru\",\"login\":\"TestLogin\",\"name\":\"Test Name\",\"birthday\":\"1990-03-01\"}";;
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(inValidUser))
                .andExpect(status().isBadRequest())
                .andDo(h -> System.out.println(h.getResponse()));
    }

    @SneakyThrows
    @Test
    public void createUserWithFailBirthdayAndReturnBadRequest() {
        inValidUser = "{\"id\":null,\"email\":\"test@yandex.ru\",\"login\":\"TestLogin\",\"name\":\"Test Name\",\"birthday\":\"2028-03-01\"}";;
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(inValidUser))
                .andExpect(status().isBadRequest())
                .andDo(h -> System.out.println(h.getResponse()));
    }

    @SneakyThrows
    @Test
    public void updateUserWithFailIdAndReturnBadRequest() {
        mockMvc.perform(put("/users").contentType(MediaType.APPLICATION_JSON).content(validUser))
                .andExpect(status().is4xxClientError())
                .andDo(h -> System.out.println(h.getResponse()));
    }
}