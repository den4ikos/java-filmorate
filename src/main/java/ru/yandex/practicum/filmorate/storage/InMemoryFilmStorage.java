package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private Long id = 0L;

    private List<Film> films = new ArrayList<>();

    private void incrementId() {
        id++;
    }

    @Override
    public List<Film> get() {
        return films;
    }

    @Override
    public Film add(Film film) {
        incrementId();
        film.setId(id);
        films.add(film);
        return film;
    }

    @Override
    public Film update(Film film) {
        Film currentFilm = films.stream().filter(f -> f.getId().equals(film.getId())).findFirst().orElseThrow(() -> new NotFoundException("There is no any film!"));

        currentFilm.setName(film.getName());
        currentFilm.setDescription(film.getDescription());
        currentFilm.setReleaseDate(film.getReleaseDate());
        currentFilm.setDuration(film.getDuration());

        return currentFilm;
    }

    @Override
    public void delete(Long filmId) {

    }
}
