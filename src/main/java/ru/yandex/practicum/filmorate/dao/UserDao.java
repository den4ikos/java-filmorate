package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

public interface UserDao {
    Map<Long, User> get();
    User add(User user);
    User update(User user);
    void delete(Long userId);
}
