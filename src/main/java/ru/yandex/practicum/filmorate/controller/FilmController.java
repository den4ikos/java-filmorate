package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.DateValidation;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class FilmController {
    List<Film> films = new ArrayList<>();

    @GetMapping("/films")
    public List<Film> findAll() {
        return films;
    }

    @PostMapping(value = "/film")
    public Film create(@Valid @RequestBody Film film) {
        if ( !(new DateValidation(film.getReleaseDate()).checkDate()) ) {
            throw new ValidationException("Release date must be greater than 1895-12-28");
        }
        films.add(film);
        return film;
    }

    @PutMapping(value = "/films/{filmId}")
    public Film update(@Valid @RequestBody Film film, @PathVariable Long filmId) {
        final List<Film> filmList = films.stream().filter(f -> Objects.equals(f.getId(), filmId)).toList();
        if (filmList.size() == 0) {
            throw new NotFoundException();
        }

        if ( !(new DateValidation(film.getReleaseDate()).checkDate()) ) {
            throw new ValidationException("Release date must be greater than 1895-12-28");
        }

        final Film currentFilm = filmList.get(0);
        currentFilm.setName(film.getName());
        currentFilm.setDescription(film.getDescription());
        currentFilm.setReleaseDate(film.getReleaseDate());
        currentFilm.setDuration(film.getDuration());
        return film;
    }
}
