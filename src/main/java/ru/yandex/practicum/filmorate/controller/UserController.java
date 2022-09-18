package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class UserController {
    private List<User> users = new ArrayList<>();

    @GetMapping(value = "/users")
    public List<User> findAll() {
        return users;
    }

    @PostMapping(value = "/user")
    public User create(@Valid @RequestBody User user) {
        users.add(getNameIfEmpty(user));
        return user;
    }

    @PutMapping(value = "/users/{userId}")
    public User update(@Valid @RequestBody User user, @PathVariable Long userId) {
        User checkedUser = getNameIfEmpty(user);
        final List<User> userById = users.stream().filter(u -> Objects.equals(u.getId(), userId)).toList();
        if (userById.size() == 0) {
            throw new NotFoundException();
        }

        final User currentUser = userById.get(0);
        currentUser.setEmail(checkedUser.getEmail());
        currentUser.setLogin(checkedUser.getLogin());
        currentUser.setName(checkedUser.getName());
        currentUser.setBirthday(checkedUser.getBirthday());
        return checkedUser;
    }

    private User getNameIfEmpty(User user) {
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return user;
    }
}
