package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.UserDbStorage;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {
    private final UserDbStorage userStorage;
    private final FilmDbStorage filmDbStorage;

    @Test
    public void getEmptyUsers() {
        Map<Long, User> users = userStorage.get();
        assertThat(users.isEmpty()).isEqualTo(true);
    }

    @Test
    public void getUnknownUserAndThrownNotFoundException() {
        assertThatThrownBy(
                () -> userStorage.getById(11111L)
        )
                .isInstanceOf(NotFoundException.class)
                .hasMessage(Constants.NO_USER);
    }

    @Test
    public void createAndReturnUser() {
        User newUser = new User();
        newUser.setName("Test Name");
        newUser.setBirthday(LocalDate.parse("1989-12-09"));
        newUser.setEmail("test@test.com");
        newUser.setLogin("TestUser");

        User createdUser = userStorage.add(newUser);
        assertThat(createdUser.getId()).isEqualTo(1);
    }

    @Test
    public void getEmptyFilms() {
        Map<Long, Film> films = filmDbStorage.get();
        assertThat(films.isEmpty()).isEqualTo(true);
    }

    @Test
    public void getUnknownFilmAndThrownNotFoundException() {
        assertThatThrownBy(
                () -> filmDbStorage.getById(1111L)
        )
                .isInstanceOf(NotFoundException.class)
                .hasMessage(Constants.NO_FILM);
    }

    @Test
    public void createAndReturnFilm() {
        Map<String, Object> mpa = new LinkedHashMap<>();
        mpa.put("id", 1);
        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("Test description");
        film.setReleaseDate(LocalDate.parse("1989-08-05"));
        film.setDuration(120);
        film.setRate(4);
        film.setMpa(mpa);

        Film createdFilm = filmDbStorage.add(film);
        assertThat(createdFilm.getId()).isEqualTo(1);
    }
}