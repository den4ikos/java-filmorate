package ru.yandex.practicum.filmorate.storage;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Constants;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Primary
@AllArgsConstructor
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Genre getById(Long id) {
        return jdbcTemplate.query(Constants.GET_GENRE_BY_ID, new GenreMapper(), id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException(Constants.NO_GENRE));
    }

    @Override
    public Map<Long, Genre> getAll() {
        return jdbcTemplate.query(Constants.GET_ALL_GENRE, new GenreMapper())
                .stream()
                .collect(Collectors.toMap(Genre::getId, Function.identity()));
    }
}
