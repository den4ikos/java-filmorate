package ru.yandex.practicum.filmorate.storage;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Constants;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component("dbFilmStorage")
@Primary
@AllArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private JdbcTemplate jdbcTemplate;

    @Override
    public Map<Long, Film> get() {
        return jdbcTemplate.query(Constants.GET_ALL_FILMS, new BeanPropertyRowMapper<>(Film.class))
                .stream()
                .collect(Collectors.toMap(Film::getId, Function.identity()));
    }

    @Override
    public Film add(Film film) {
        jdbcTemplate.update(
                Constants.ADD_FILM,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRate(),
                film.getMpa().get("id")
        );

        return jdbcTemplate.queryForObject(Constants.GET_FILM, new FilmRowMapper());
    }

    @Override
    public Film update(Film film) {
        Film filmFromDb = getById(film.getId());
        jdbcTemplate.update(
                "update films set name = ?, description = ?, releaseDate = ?, duration = ? where id = ?",
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                filmFromDb.getId()
        );

        return film;
    }

    @Override
    public Film getById(Long id) {
        return jdbcTemplate.query(Constants.GET_FILM_BY_ID, new BeanPropertyRowMapper<>(Film.class), id)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundException(Constants.NO_FILM));
    }

    @Override
    public void delete(Long filmId) {

    }

    @Override
    public List<Film> find(Map<String, String> params) {
        StringBuilder sql = new StringBuilder("SELECT films.*");
        int count = params.containsKey("count") ? Integer.parseInt(params.get("count")) : 10;
        if (params.containsKey("count")) {
            sql.append(", COUNT(likes.*) AS total FROM films LEFT JOIN likes ON (films.id = likes.film_id) GROUP BY films.id ORDER BY total DESC LIMIT ?");
        } else {
            sql.append(" FROM films LIMIT ?");
        }

        return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(Film.class), count);
    }

    @Override
    public void addLikeToFilm(Film film, Long userId) {
        jdbcTemplate.update(Constants.ADD_LIKE, userId, film.getId());
    }

    @Override
    public void deleteLike(Film film, Long userId) {
        jdbcTemplate.update(Constants.REMOVE_LIKE, userId, film.getId());
    }
}
