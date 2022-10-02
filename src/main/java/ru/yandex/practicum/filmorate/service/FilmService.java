package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.validation.DateValidation;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FilmService {
    private FilmStorage filmStorage;
    private List<Film> films = new ArrayList<>();
    private Long id = 0L;

    @Autowired
    public FilmService(InMemoryFilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    private void incrementId() {
        id++;
    }

    public List<Film> findAll() {
        return films;
    }

    public Film create(Film film) {
        incrementId();
        film.setId(id);
        if ( !DateValidation.checkDate(film.getReleaseDate()) ) {
            log.error("Release date must be greater than 1895-12-28");
            throw new ValidationException("Release date must be greater than 1895-12-28");
        }
        films.add(film);
        return film;
    }

    public Film update(Film film) {
        if ( !DateValidation.checkDate(film.getReleaseDate()) ) {
            log.error("Release date must be greater than 1895-12-28");
            throw new ValidationException("Release date must be greater than 1895-12-28");
        }

        Film currentFilm = films.stream().filter(f -> f.getId().equals(film.getId())).findFirst().orElseThrow(() -> {
            log.error("There is no any film");
            return new NotFoundException();
        });

        currentFilm.setName(film.getName());
        currentFilm.setDescription(film.getDescription());
        currentFilm.setReleaseDate(film.getReleaseDate());
        currentFilm.setDuration(film.getDuration());

        return currentFilm;
    }
}
