package ru.yandex.practicum.filmorate.validation;

import ru.yandex.practicum.filmorate.Constants;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Validation {
    public static boolean checkDate(LocalDate date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constants.FORMAT);
        LocalDate d = LocalDate.parse(Constants.DATE_BEFORE, dtf);
        return date.isAfter(d);
    }

    public static User checkUserName(User user) {
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return user;
    }
}
