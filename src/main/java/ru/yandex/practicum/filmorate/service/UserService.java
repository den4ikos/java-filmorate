package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        if (!userStorage.get().containsKey(id)) {
            throw new NotFoundException("There is no any user!");
        }
        return userStorage.get().get(id);
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

    public List<User> getFriends(Long userId) {
        List<User> friends = new ArrayList<>();
        User user = getById(userId);
        Set<Long> friendsIds = user.getFriends();

        if (friendsIds.size() > 0) {
            for (Long id: friendsIds) {
                friends.add(getById(id));
            }
        }

        return friends;
    }

    public List<User> getCommonFriends(Long userId, Long otherId) {
        List<User> friends = new ArrayList<>();
        User user = getById(userId);
        User otherUser = getById(otherId);
        Set<Long> userFriends = user.getFriends();

        if (userFriends.size() > 0) {
            for (Long id: userFriends) {
                if (otherUser.getFriends().contains(id)) {
                    friends.add(getById(id));
                }
            }
        }

        return friends;
    }
}
