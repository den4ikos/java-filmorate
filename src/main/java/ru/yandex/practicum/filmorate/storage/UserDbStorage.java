package ru.yandex.practicum.filmorate.storage;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Constants;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validation.Validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component("dbUserStorage")
@Primary
@AllArgsConstructor
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Map<Long, User> get() {
        return jdbcTemplate
                .query(Constants.SELECT_USERS, new UserRowMapper())
                .stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));
    }

    @Override
    public User add(User user) {
        User validUser = Validation.checkUserName(user);
        jdbcTemplate.update(
                Constants.INSERT_USER,
                validUser.getName(),
                validUser.getEmail(),
                validUser.getLogin(),
                validUser.getBirthday());
        return jdbcTemplate.queryForObject(Constants.FIND_LAST_USER, new UserRowMapper());
    }

    @Override
    public User update(User user) {
        int numberOfRecords = jdbcTemplate.update(
                Constants.UPDATE_USER,
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getBirthday(),
                user.getId()
        );

        if (numberOfRecords == 0) {
            throw new NotFoundException(Constants.NO_USER);
        }

        return user;
    }

    @Override
    public User getById(Long id) {
        return jdbcTemplate.query(Constants.FIND_USER, new UserRowMapper(), id)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundException(Constants.NO_USER));
    }

    @Override
    public void delete(Long userId) {
        int numberOfRecords = jdbcTemplate.update(
                Constants.DELETE_USER,
                userId
        );

        if (numberOfRecords == 0) {
            throw new NotFoundException(Constants.NO_USER);
        }
    }

    @Override
    public void addFriends(Long userId, Long friendId) {
        User user = getById(userId);
        User friend = getById(friendId);
        jdbcTemplate.update(Constants.ADD_FRIEND, user.getId(), friend.getId());
        jdbcTemplate.update(Constants.ADD_FRIEND, friend.getId(), user.getId());
    }

    @Override
    public List<User> getFriends(Long userId) {
        return jdbcTemplate.query(Constants.GET_USER_FRIENDS, new UserRowMapper(), userId);
    }

    @Override
    public List<User> getCommonFriends(User user, User otherUser) {
        return jdbcTemplate.query(
                Constants.GET_COMMON_FRIENDS,
                new UserRowMapper(),
                user.getId(),
                otherUser.getId()
        );
    }

    @Override
    public void deleteFriends(User user, User friend) {
        Long userId = user.getId();
        Long friendId = user.getId();
        jdbcTemplate.update(
                Constants.DELETE_FRIENDS,
                userId,
                friendId,
                friendId,
                userId);
    }
}
