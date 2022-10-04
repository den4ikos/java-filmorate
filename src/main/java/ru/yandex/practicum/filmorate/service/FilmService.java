package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Constants;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.validation.DateValidation;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class FilmService {
    private FilmStorage filmStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public List<Film> findAll() {
        return filmStorage.get();
    }

    public Film create(Film film) {
        if (!DateValidation.checkDate(film.getReleaseDate())) {
            log.error("Release date must be greater than " + Constants.DATE_BEFORE);
            throw new ValidationException("Release date must be greater than " + Constants.DATE_BEFORE);
        }

        return filmStorage.add(film);
    }

    public Film update(Film film) {
        if (!DateValidation.checkDate(film.getReleaseDate())) {
            log.error("Release date must be greater than " + Constants.DATE_BEFORE);
            throw new ValidationException("Release date must be greater than " + Constants.DATE_BEFORE);
        }
        return filmStorage.update(film);
    }

    public Film getById(Long filmId) {
        return filmStorage.get().stream().filter(film -> film.getId().equals(filmId)).findFirst().orElseThrow(() -> {
            log.error("There is no any film!");
            return new NotFoundException("There is no any film!");
        });
    }

    public void addLike(Long filmId, Long userId) {
        if (userId <= 0) {
            throw new NotFoundException("There is no any user!");
        }
        Film film = getById(filmId);
        film.setLike(userId);
    }

    public void deleteLike(Long filmId, Long userId) {
        if (userId <= 0) {
            throw new NotFoundException("There is no any user!");
        }
        Film film = getById(filmId);
        film.getLikes().remove(userId);
    }

    public List<Film> findByParams(Map<String, String> params) {
        Stream<Film> results = filmStorage.get().stream();
        if (params.containsKey("count")) {
            results = results
                    .sorted((f0, f1) -> f1.getLikes().size() - f0.getLikes().size())
                    .limit(Integer.parseInt(params.get("count")));
        } else {
            results = results.limit(10);
        }
        return results.collect(Collectors.toList());
    }
}
