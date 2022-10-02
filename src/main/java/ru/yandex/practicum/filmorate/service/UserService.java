package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    private UserStorage userStorage;
    private Long id = 0L;
    private List<User> users = new ArrayList<>();

    @Autowired
    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    private void incrementId() {
        id++;
    }

    public List<User> findAll() {
        return users;
    }

    public User create(User user) {
        incrementId();
        user.setId(id);
        users.add(getNameIfEmpty(user));
        return user;
    }

    public User update(User user) {
        User validUser = getNameIfEmpty(user);
        User currentUser = users.stream()
                .filter(u -> u.getId().equals(user.getId()))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("There is no any user!");
                    return new NotFoundException();
                });
        currentUser.setEmail(validUser.getEmail());
        currentUser.setLogin(validUser.getLogin());
        currentUser.setName(validUser.getName());
        currentUser.setBirthday(validUser.getBirthday());

        return currentUser;
    }

    public User getById(Long id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElseThrow(() -> {
            log.error("There is no any user!");
            return new NotFoundException();
        });
    }

    public void adFriend(Long userId, Long friendId) {
        User user = getById(userId);
        User friend = getById(friendId);

        user.setFriends(friend.getId());
        friend.setFriends(user.getId());
    }

    public void deleteFriend(Long userId, Long friendId) {
        User user = getById(userId);
        User friend = getById(friendId);

        user.getFriends().remove(friend.getId());
        friend.getFriends().remove(user.getId());
    }

    private User getNameIfEmpty(User user) {
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return user;
    }
}
