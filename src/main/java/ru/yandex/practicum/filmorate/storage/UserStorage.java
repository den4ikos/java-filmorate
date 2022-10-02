package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage {
    User add(User user);
    User update(User user);
    void delete(Long userId);
}
