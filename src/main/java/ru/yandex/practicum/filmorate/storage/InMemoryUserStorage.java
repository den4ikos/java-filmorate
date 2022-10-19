package ru.yandex.practicum.filmorate.storage;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.HashMap;
import java.util.Map;

@Component("inMemoryUserStorage")
public class InMemoryUserStorage implements UserStorage {
    private Long id = 0L;

    private final Map<Long, User> users = new HashMap<>();

    private void incrementId() {
        id++;
    }

    @Override
    public Map<Long, User> get() {
        return users;
    }
    @Override
    public User add(User user) {
        incrementId();
        user.setId(id);
        users.put(user.getId(), getNameIfEmpty(user));
        return user;
    }

    @Override
    public User update(User user) {
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("There is no any user!");
        }
        User validUser = getNameIfEmpty(user);
        User currentUser = users.get(validUser.getId());
        currentUser.setEmail(validUser.getEmail());
        currentUser.setLogin(validUser.getLogin());
        currentUser.setName(validUser.getName());
        currentUser.setBirthday(validUser.getBirthday());
        return currentUser;
    }

    @Override
    public void delete(Long userId) {
        users.remove(userId);
    }

    private User getNameIfEmpty(User user) {
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return user;
    }
}
