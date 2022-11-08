package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Constants;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.validation.Validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Qualifier("filmDbStorage")
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;

    public List<Film> findAll() {
        List<Film> films = new ArrayList<>();
        if (filmStorage.get().size() > 0) {
            films.addAll(filmStorage.get().values());
        }
        return films;
    }

    public Film create(Film film) {
        if (!Validation.checkDate(film.getReleaseDate())) {
            log.error("Release date must be greater than " + Constants.DATE_BEFORE);
            throw new ValidationException("Release date must be greater than " + Constants.DATE_BEFORE);
        }

        return filmStorage.add(film);
    }

    public Film update(Film film) {
        if (!Validation.checkDate(film.getReleaseDate())) {
            log.error("Release date must be greater than " + Constants.DATE_BEFORE);
            throw new ValidationException("Release date must be greater than " + Constants.DATE_BEFORE);
        }
        return filmStorage.update(film);
    }

    public Film getById(Long filmId) {
        return filmStorage.getById(filmId);
    }

    public void addLike(Long filmId, Long userId) {
        if (userId <= 0) {
            throw new NotFoundException(Constants.NO_USER);
        }
        Film film = getById(filmId);
        filmStorage.addLikeToFilm(film, userId);
    }

    public void deleteLike(Long filmId, Long userId) {
        if (userId <= 0) {
            throw new NotFoundException(Constants.NO_USER);
        }
        Film film = getById(filmId);
        filmStorage.deleteLike(film, userId);
    }

public List<Film> findByParams(Map<String, String> params) {
        return filmStorage.findAllByParams(params);
    }
}
