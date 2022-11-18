package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Map;

public interface GenreStorage {
    Genre getById(Long id);
    Map<Long, Genre> getAll();
}
