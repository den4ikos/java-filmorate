package ru.yandex.practicum.filmorate.validation;

import ru.yandex.practicum.filmorate.Constants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateValidation {
    public static boolean checkDate(LocalDate date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constants.FORMAT);
        LocalDate d = LocalDate.parse(Constants.DATE_BEFORE, dtf);
        return date.isAfter(d);
    }
}
