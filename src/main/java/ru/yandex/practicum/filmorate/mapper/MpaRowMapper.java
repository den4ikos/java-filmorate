package ru.yandex.practicum.filmorate.mapper;

import ru.yandex.practicum.filmorate.model.Mpa;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MpaRowMapper implements RowMapper<Mpa> {
    @Override
    public Mpa mapRow(ResultSet rs, int rowNum) throws SQLException {
        Mpa mpa = new Mpa();
        mpa.setId(rs.getLong("id"));
        mpa.setName(rs.getString("name"));
        mpa.setShortDescription(rs.getString("short_description"));

        return mpa;
    }
}
