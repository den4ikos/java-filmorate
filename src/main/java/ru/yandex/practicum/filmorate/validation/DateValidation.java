package ru.yandex.practicum.filmorate.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateValidation {
    private static final String FORMAT = "yyyy-MM-dd";
    private static final String DATE_BEFORE = "1895-12-28";
    private LocalDate date;
    public DateValidation(LocalDate date) {
        this.date = date;
    }

    public boolean checkDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(FORMAT);
        LocalDate d = LocalDate.parse(DATE_BEFORE, dtf);
        return date.isAfter(d);
    }
}