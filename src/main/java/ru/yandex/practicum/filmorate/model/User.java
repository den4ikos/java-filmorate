package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private Long id;

    @NotNull
    @Email
    private String email;

    @NotNull
    @NotBlank
    @NotEmpty
    private String login;

    private String name;

    @Past(message = "Must contain the past date")
    private LocalDate birthday;

    private Set<Long> friends = new HashSet<>();

    public void setFriends(Long friendId) {
        friends.add(friendId);
    }

    public void deleteFriends(User friend) {
        friend.getFriends().remove(id);
        this.getFriends().remove(friend.getId());
    }
}
