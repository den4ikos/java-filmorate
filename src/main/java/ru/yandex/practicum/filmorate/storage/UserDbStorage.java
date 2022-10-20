package ru.yandex.practicum.filmorate.storage;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validation.Validation;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component("dbUserStorage")
@Primary
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Map<Long, User> get() {
        String query = "select * from users order by name";
        return jdbcTemplate
                .query(query, new UserRowMapper())
                .stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));
    }

    @Override
    public User add(User user) {
        User validUser = Validation.checkUserName(user);
        jdbcTemplate.update(
                "insert into users (name, email, login, birthday) values (?, ?, ?, ?)",
                validUser.getName(),
                validUser.getEmail(),
                validUser.getLogin(),
                validUser.getBirthday());
        return jdbcTemplate.queryForObject("select * from users order by id desc limit 1", new UserRowMapper());
    }

    @Override
    public User update(User user) {
        String sql = "update users set name = ?, email = ?, login = ?, birthday = ? where id = ?";

        int numberOfRecords = jdbcTemplate.update(
                sql,
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getBirthday(),
                user.getId()
        );

        if (numberOfRecords == 0) {
            throw new NotFoundException("There is no any user!");
        }

        return user;
    }

    @Override
    public User getById(Long id) {
        String sql = "select * from users where id = ? limit 1";
        try {
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException("There is no any user!");
        }
    }

    @Override
    public void delete(Long userId) {
        String sql = "delete from users where id = ?";
        int numberOfRecords = jdbcTemplate.update(
                sql,
                userId
        );

        if (numberOfRecords == 0) {
            throw new NotFoundException("There is no any user!");
        }
    }

    @Override
    public void addFriends(Long userId, Long friendId) {
        User user = getById(userId);
        User friend = getById(friendId);

        String sql = "insert into friends set user_id = ?, friend_id = ?, friend_status_id = ?";
        jdbcTemplate.update(sql, user.getId(), friend.getId(), 1);
    }
}
