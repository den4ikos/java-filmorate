package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
public class UserController {
    private List<User> users = new ArrayList<>();

    @GetMapping(value = "/users")
    public List<User> findAll(HttpServletRequest request) {
        log.info("Endpoint request received: '{} {}', Query Parameter String: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        return users;
    }

    @PostMapping(value = "/user")
    public User create(@Valid @RequestBody User user) {
        users.add(getNameIfEmpty(user));
        log.info("Endpoint request received: 'POST/user' {}'", user.toString());
        return user;
    }

    @PutMapping(value = "/users/{userId}")
    public User update(@Valid @RequestBody User user, @PathVariable Long userId) {
        User checkedUser = getNameIfEmpty(user);
        final List<User> userById = users.stream().filter(u -> Objects.equals(u.getId(), userId)).toList();
        if (userById.size() == 0) {
            log.info("There is no any user!");
            throw new NotFoundException();
        }

        final User currentUser = userById.get(0);
        currentUser.setEmail(checkedUser.getEmail());
        currentUser.setLogin(checkedUser.getLogin());
        currentUser.setName(checkedUser.getName());
        currentUser.setBirthday(checkedUser.getBirthday());
        log.info("Endpoint request received: 'POST/films/{} {}'", userId, user.toString());
        return checkedUser;
    }

    private User getNameIfEmpty(User user) {
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return user;
    }
}
