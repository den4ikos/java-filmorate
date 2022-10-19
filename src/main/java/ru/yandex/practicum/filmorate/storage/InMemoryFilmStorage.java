package ru.yandex.practicum.filmorate.storage;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.HashMap;
import java.util.Map;

@Component("inMemoryFilmStorage")
public class InMemoryFilmStorage implements FilmStorage {
    private Long id = 0L;

    private final Map<Long, Film> films = new HashMap<>();

    private void incrementId() {
        id++;
    }

    @Override
    public Map<Long, Film> get() {
        return films;
    }

    @Override
    public Film add(Film film) {
        incrementId();
        film.setId(id);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("There is no any film!");
        }

        Film currentFilm = films.get(film.getId());
        currentFilm.setName(film.getName());
        currentFilm.setDescription(film.getDescription());
        currentFilm.setReleaseDate(film.getReleaseDate());
        currentFilm.setDuration(film.getDuration());

        return currentFilm;
    }

    @Override
    public void delete(Long filmId) {
        if (!films.containsKey(filmId)) {
            throw new NotFoundException("There is no any film!");
        }
        films.remove(filmId);
    }
}
