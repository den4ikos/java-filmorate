package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryUserStorage implements UserStorage {
    private Long id = 0L;

    private List<User> users = new ArrayList<>();

    private void incrementId() {
        id++;
    }

    @Override
    public List<User> get() {
        return users;
    }
    @Override
    public User add(User user) {
        incrementId();
        user.setId(id);
        users.add(getNameIfEmpty(user));
        return user;
    }

    @Override
    public User update(User user) {
        User validUser = getNameIfEmpty(user);
        User currentUser = users.stream()
                .filter(u -> u.getId().equals(user.getId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("There is no any user!"));
        currentUser.setEmail(validUser.getEmail());
        currentUser.setLogin(validUser.getLogin());
        currentUser.setName(validUser.getName());
        currentUser.setBirthday(validUser.getBirthday());
        return currentUser;
    }

    @Override
    public void delete(Long userId) {

    }

    private User getNameIfEmpty(User user) {
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return user;
    }
}
