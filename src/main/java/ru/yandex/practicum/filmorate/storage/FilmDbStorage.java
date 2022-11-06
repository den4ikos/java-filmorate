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

import java.sql.ResultSet;
import java.util.*;
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

        Film createdFilm = jdbcTemplate.queryForObject(Constants.GET_FILM, new FilmRowMapper());

        if (!film.getGenres().isEmpty()) {
            StringBuilder sql = new StringBuilder("INSERT INTO film_genre (film_id, genre_id) VALUES ");

            int j = 1;
            String delimiter = ", ";
            List<Map<String, Object>> genres = new ArrayList<>();

            for (Map<String, Object> f : film.getGenres()) {
                Map<String, Object> g = new HashMap<>();
                g.put("id", f.get("id"));
                genres.add(g);
                if (film.getGenres().size() == j) {
                    delimiter = ";";
                }
                sql.append("("+ createdFilm.getId() +", "+ f.get("id") +")" + delimiter);

                j++;
            }

            jdbcTemplate.update(sql.toString());

            createdFilm.setGenres(genres);
        }

        return createdFilm;
    }

    @Override
    public Film update(Film film) {
        Film filmFromDb = getById(film.getId());
        jdbcTemplate.update(
                "update films set name = ?, description = ?, releaseDate = ?, duration = ?, rate = ?, mpa_id = ? where id = ?",
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRate(),
                film.getMpa().get("id"),
                filmFromDb.getId()
        );

        return film;
    }

    @Override
    public Film getById(Long id) {
        Film film = jdbcTemplate.query(Constants.GET_FILM_BY_ID, new FilmRowMapper(), id)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundException(Constants.NO_FILM));

        Map<String, Object> ids = jdbcTemplate.query(
                "SELECT fg.genre_id AS id, g.name FROM film_genre fg LEFT JOIN genres g ON (fg.genre_id = g.id) WHERE film_id = ?",
                (ResultSet rs) -> {
                    Map<String, Object> results = new HashMap<>();
                    while (rs.next()) {
                        results.put("id", rs.getInt("id"));
                        results.put("name", rs.getString("name"));
                    }

                    return results;
                },
                id);

        if (ids != null && !ids.isEmpty()) {
            film.getGenres().add(ids);
        }

        return film;
    }

    @Override
    public void delete(Long filmId) {

    }

    @Override
    public List<Film> find(Map<String, String> params) {
        StringBuilder sql = new StringBuilder("SELECT films.*, mpa.name as mpa_name ");
        String selectMpa = " LEFT JOIN motion_picture_associations mpa ON (films.mpa_id = mpa.id) ";
        int count = params.containsKey("count") ? Integer.parseInt(params.get("count")) : 10;
        if (params.containsKey("count")) {
            sql.append(", (SELECT COUNT(*) FROM likes WHERE likes.film_id = films.id) AS total FROM films");
            sql.append(selectMpa);
            sql.append("GROUP BY films.id ORDER BY total DESC LIMIT ?");
        } else {
            sql.append(" FROM films");
            sql.append(selectMpa);
            sql.append(" LIMIT ?");
        }
        System.out.println(sql);
        return jdbcTemplate.query(sql.toString(), new FilmRowMapper(), count);
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
