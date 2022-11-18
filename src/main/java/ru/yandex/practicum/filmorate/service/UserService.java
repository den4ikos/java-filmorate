package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        if (userStorage.get().size() > 0) {
            users.addAll(userStorage.get().values());
        }
        return users;
    }

    public User create(User user) {
        return userStorage.add(user);
    }

    public User update(User user) {
        return userStorage.update(user);
    }

    public User getById(Long id) {
        return userStorage.getById(id);
    }

    public void addFriend(Long userId, Long friendId) {
        userStorage.addFriends(userId, friendId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        User user = getById(userId);
        User friend = getById(friendId);
        userStorage.deleteFriends(user, friend);
    }

    public List<User> getFriends(Long userId) {
        return userStorage.getFriends(userId);
    }

    public List<User> getCommonFriends(Long userId, Long otherId) {
        User user = getById(userId);
        User otherUser = getById(otherId);
        return userStorage.getCommonFriends(user, otherUser);
    }
}
