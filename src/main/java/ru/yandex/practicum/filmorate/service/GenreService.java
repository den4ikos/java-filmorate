package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Qualifier("dbGenreStorage")
@RequiredArgsConstructor
public class GenreService {
    private final GenreStorage genreStorage;

    public Genre getById(Long id) {
        return genreStorage.getById(id);
    }

    public List<Genre> getAll() {
        List<Genre> genres = new ArrayList<>();
        Map<Long, Genre> genresFromQuery = genreStorage.get();

        if (!genresFromQuery.isEmpty()) {
            genres.addAll(genresFromQuery.values());
        }
        return genres;
    }
}
