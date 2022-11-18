package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;

public interface UserStorage {
    Map<Long, User> get();
    User add(User user);
    User update(User user);
    User getById(Long id);
    void delete(Long userId);
    void addFriends(Long userId, Long friendId);
    List<User> getFriends(Long userId);
    List<User> getCommonFriends(User u1, User u2);
    void deleteFriends(User user, User friend);
}
