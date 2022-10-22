package ru.yandex.practicum.filmorate.storage;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Constants;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validation.Validation;

import java.util.*;

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
        users.put(user.getId(), Validation.checkUserName(user));
        return user;
    }

    @Override
    public User update(User user) {
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException(Constants.NO_USER);
        }
        User validUser = Validation.checkUserName(user);
        User currentUser = users.get(validUser.getId());
        currentUser.setEmail(validUser.getEmail());
        currentUser.setLogin(validUser.getLogin());
        currentUser.setName(validUser.getName());
        currentUser.setBirthday(validUser.getBirthday());
        return currentUser;
    }

    @Override
    public User getById(Long id) {
        if (!get().containsKey(id)) {
            throw new NotFoundException(Constants.NO_USER);
        }

        return get().get(id);
    }

    @Override
    public void delete(Long userId) {
        users.remove(userId);
    }

    @Override
    public void addFriends(Long userId, Long friendId) {
        User user = getById(userId);
        User friend = getById(friendId);

        user.setFriends(friend.getId());
        friend.setFriends(user.getId());
    }

    @Override
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

    @Override
    public List<User> getCommonFriends(User user, User otherUser) {
        List<User> friends = new ArrayList<>();
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

    @Override
    public void deleteFriends(User user, User friend) {
        user.getFriends().remove(friend.getId());
        friend.getFriends().remove(user.getId());
    }
}
