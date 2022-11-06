package ru.yandex.practicum.filmorate.storage;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Constants;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.MpaRowMapper;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component("mpaDbStorage")
@Primary
@AllArgsConstructor
public class MpaDbStorage implements MpaStorage {
    private JdbcTemplate jdbcTemplate;

    @Override
    public Mpa getById(Long id) {
        return jdbcTemplate.query("SELECT * FROM motion_picture_associations WHERE id = ?", new MpaRowMapper(), id)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundException(Constants.NO_MPA));
    }

    @Override
    public Map<Long, Mpa> get() {
        return jdbcTemplate.query("SELECT * FROM motion_picture_associations ORDER BY id", new MpaRowMapper())
                .stream()
                .collect(Collectors.toMap(Mpa::getId, Function.identity()));
    }
}
