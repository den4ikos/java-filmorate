package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Map;

public interface FilmStorage {
    Map<Long, Film> get();
    Film add(Film film);
    Film update(Film film);
    Film getById(Long id);
    void delete(Long filmId);
    List<Film> find(Map<String, String> params);
    void addLikeToFilm(Film film, Long userId);
    void deleteLike(Film film, Long userId);
}
