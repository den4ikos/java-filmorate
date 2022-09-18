package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

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
    public Film create(@RequestBody Film film) {
        films.add(film);
        return film;
    }

    @PutMapping(value = "/films/{filmId}")
    public Film update(@RequestBody Film film, @PathVariable Long filmId) {
        final List<Film> filmList = films.stream().filter(f -> Objects.equals(f.getId(), filmId)).toList();
        if (filmList.size() == 0) {
            throw new NotFoundException();
        }

        final Film currentFilm = filmList.get(0);
        currentFilm.setName(film.getName());
        currentFilm.setDescription(film.getDescription());
        currentFilm.setReleaseDate(film.getReleaseDate());
        currentFilm.setDuration(film.getDuration());
        return film;
    }
}
