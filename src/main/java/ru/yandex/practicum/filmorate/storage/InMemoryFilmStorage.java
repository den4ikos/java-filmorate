package ru.yandex.practicum.filmorate.storage;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
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
        Film currentFilm = getById(film.getId());
        currentFilm.setName(film.getName());
        currentFilm.setDescription(film.getDescription());
        currentFilm.setReleaseDate(film.getReleaseDate());
        currentFilm.setDuration(film.getDuration());

        return currentFilm;
    }

    @Override
    public Film getById(Long id) {
        if (!films.containsKey(id)) {
            throw new NotFoundException("There is no any film!");
        }

        return films.get(id);
    }

    @Override
    public void delete(Long filmId) {
        if (!films.containsKey(filmId)) {
            throw new NotFoundException("There is no any film!");
        }
        films.remove(filmId);
    }

    @Override
    public List<Film> findAllByParams(Map<String, String> params) {
        Stream<Film> results = get().values().stream();
        if (params.containsKey("count")) {
            results = results
                    .sorted((f0, f1) -> f1.getLikes().size() - f0.getLikes().size())
                    .limit(Integer.parseInt(params.get("count")));
        } else {
            results = results.limit(10);
        }
        return results.collect(Collectors.toList());
    }

    @Override
    public void addLikeToFilm(Film film, Long userId) {
        film.addLike(userId);
    }

    @Override
    public void deleteLike(Film film, Long userId) {
        film.getLikes().remove(userId);
    }
}
