package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.service.MpaService;
import ru.yandex.practicum.filmorate.service.UserService;

import static org.assertj.core.api.Assertions.*;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {
    private final UserService userService;

    private final FilmService filmService;

    private final MpaService mpaService;

    private final GenreService genreService;

    @Test
    public void getEmptyUsers() {
        List<User> users = userService.findAll();
        assertThat(users.isEmpty()).isEqualTo(true);
    }

    @Test
    public void getUnknownUserAndThrownNotFoundException() {
        assertThatThrownBy(
                () -> userService.getById(11111L)
        )
                .isInstanceOf(NotFoundException.class)
                .hasMessage(Constants.NO_USER);
    }

    @Test
    public void userCreateWithoutName() {
        User user = new User();
        user.setLogin("test-test");
        user.setName("");
        user.setEmail("yandex@yandex.ru");
        user.setBirthday(LocalDate.parse("1990-08-20"));
        User newUser = userService.create(user);
        assertThat(newUser.getName()).isEqualTo("test-test");
    }

    @Test
    public void createFailUserWithNullEmail() {
        User user = new User();
        user.setLogin("test-test");
        user.setName("test");
        user.setEmail(null);
        user.setBirthday(LocalDate.parse("1990-08-20"));
        assertThatThrownBy(
                () -> userService.create(user)
        ).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void createAndReturnUser() {
        User user = new User();
        user.setName("Test Name");
        user.setBirthday(LocalDate.parse("1989-12-09"));
        user.setEmail("test@test.com");
        user.setLogin("TestUser");

        User createdUser = userService.create(user);
        assertThat(createdUser.getEmail()).isEqualTo("test@test.com");
    }

    @Test
    public void getEmptyFilms() {
        List<Film> films = filmService.findAll();
        assertThat(films.isEmpty()).isEqualTo(true);
    }

    @Test
    public void getUnknownFilmAndThrownNotFoundException() {
        assertThatThrownBy(
                () -> filmService.getById(1111L)
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

        Film createdFilm = filmService.create(film);
        assertThat(createdFilm.getId()).isEqualTo(1);
    }

    @Test
    public void createFilmWithBadReleaseDate() {
        Map<String, Object> mpa = new LinkedHashMap<>();
        mpa.put("id", 1);
        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("Test description");
        film.setReleaseDate(LocalDate.parse("1890-03-25"));
        film.setDuration(120);
        film.setRate(4);
        film.setMpa(mpa);

        assertThatThrownBy(() -> filmService.create(film))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Release date must be greater than " + Constants.DATE_BEFORE);
    }

    @Test
    public void addLikeToNonExistentFilm() {
        assertThatThrownBy(() -> filmService.addLike(1L, 1L))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void getAllMpa() {
        List<Mpa> mpaAll = mpaService.getAll();
        assertThat(mpaAll.size() > 0).isEqualTo(true);
    }

    @Test
    public void getNonExistentMpaThrowNotFoundException() {
        assertThatThrownBy(() -> mpaService.findById(111L))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void getAllGenres() {
        List<Genre> genres = genreService.getAll();
        assertThat(genres.size() > 0).isEqualTo(true);
    }

    @Test
    public void getNonExistentGenreThrowNotFoundException() {
        assertThatThrownBy(() -> genreService.getById(111L))
                .isInstanceOf(NotFoundException.class);
    }
}