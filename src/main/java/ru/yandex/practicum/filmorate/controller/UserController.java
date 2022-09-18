package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@RestController
public class UserController {
    private List<User> users = new ArrayList<>();

    @GetMapping(value = "/users")
    public List<User> findAll() {
        return users;
    }

    @PostMapping(value = "/user")
    public User create(@RequestBody User user) {
        users.add(user);
        return user;
    }

    @PutMapping(value = "/users/{userId}")
    public User update(@RequestBody User user, @PathVariable Long userId) {
        final List<User> userById = users.stream().filter(u -> Objects.equals(u.getId(), userId)).toList();
        if (userById.size() == 0) {
            throw new NotFoundException();
        }

        final User currentUser = userById.get(0);
        currentUser.setEmail(user.getEmail());
        currentUser.setLogin(user.getLogin());
        currentUser.setName(user.getName());
        currentUser.setBirthday(user.getBirthday());
        return user;
    }
}
