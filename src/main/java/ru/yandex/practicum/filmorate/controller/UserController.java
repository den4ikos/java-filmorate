package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping(value = "/users")
    public List<User> findAll(HttpServletRequest request) {
        log.info("Endpoint request received: '{} {}', Query Parameter String: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        return userService.findAll();
    }

    @GetMapping(value = "/users/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PostMapping(value = "/users")
    public User create(@Valid @RequestBody User user) {
        log.info("Endpoint request received: 'POST/user' {}'", user.toString());
        return userService.create(user);
    }

    @PutMapping(value = "/users")
    public User update(@Valid @RequestBody User user) {
        log.info("Endpoint request received: 'PUT/users/{}'", user);
        return userService.update(user);
    }

    @PutMapping(value = "/users/{id}/friends/{friendId}")
    public void addFriendToUser(@PathVariable("id") Long userId, @PathVariable Long friendId) {
        log.info("Endpoint request received: 'PUT/users/{}/friends/{}'", userId, friendId);
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping(value = "/users/{id}/friends/{friendId}")
    public void deleteFriendFromUser(@PathVariable("id") Long userId, @PathVariable Long friendId) {
        log.info("Endpoint request received: 'DELETE/users/{}/friends/{}'", userId, friendId);
        userService.deleteFriend(userId, friendId);
    }

    @GetMapping(value = "/users/{id}/friends")
    public List<User> getUserFriends(@PathVariable("id") Long userId) {
        log.info("Endpoint request received: 'GET/users/{}'", userId);
        return userService.getFriends(userId);
    }

    @GetMapping(value = "/users/{id}/friends/common/{otherId}")
    public List<User> getCommonUserFriends(@PathVariable("id") Long userId, @PathVariable("otherId") Long otherUserId) {
        log.info("Endpoint request received: 'GET/users/{}/friends/common/{}'", userId, otherUserId);
        return userService.getCommonFriends(userId, otherUserId);
    }
}
