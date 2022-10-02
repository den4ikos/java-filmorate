package ru.yandex.practicum.filmorate.exception;

import java.time.LocalDate;

public class ValidationException extends RuntimeException{
    private String message;
    public ValidationException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "You have error: " + message;
    }
}
