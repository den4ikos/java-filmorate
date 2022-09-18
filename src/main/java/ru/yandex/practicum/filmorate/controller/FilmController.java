package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.DateValidation;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
public class FilmController {
    List<Film> films = new ArrayList<>();

    @GetMapping("/films")
    public List<Film> findAll(HttpServletRequest request) {
        log.info("Endpoint request received: '{} {}', Query Parameter String: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        return films;
    }

    @PostMapping(value = "/film")
    public Film create(@Valid @RequestBody Film film) {
        if ( !(new DateValidation(film.getReleaseDate()).checkDate()) ) {
            log.error("Release date must be greater than 1895-12-28");
            throw new ValidationException("Release date must be greater than 1895-12-28");
        }
        films.add(film);
        log.info("Endpoint request received: 'POST/film {}'", film.toString());
        return film;
    }

    @PutMapping(value = "/films/{filmId}")
    public Film update(@Valid @RequestBody Film film, @PathVariable Long filmId) {
        final List<Film> filmList = films.stream().filter(f -> Objects.equals(f.getId(), filmId)).toList();
        if (filmList.size() == 0) {
            log.error("There is no any film");
            throw new NotFoundException();
        }

        if ( !(new DateValidation(film.getReleaseDate()).checkDate()) ) {
            log.error("Release date must be greater than 1895-12-28");
            throw new ValidationException("Release date must be greater than 1895-12-28");
        }

        final Film currentFilm = filmList.get(0);
        currentFilm.setName(film.getName());
        currentFilm.setDescription(film.getDescription());
        currentFilm.setReleaseDate(film.getReleaseDate());
        currentFilm.setDuration(film.getDuration());
        log.info("Endpoint request received: 'POST/films/{} {}'", filmId, film.toString());
        return film;
    }
}
