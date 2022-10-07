package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Map;

public interface FilmStorage {
    Map<Long, Film> get();
    Film add(Film film);
    Film update(Film film);
    void delete(Long filmId);
}
