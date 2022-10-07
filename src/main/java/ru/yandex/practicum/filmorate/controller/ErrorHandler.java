package ru.yandex.practicum.filmorate.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.util.Map;

@RestControllerAdvice(basePackages = "ru.yandex.practicum.filmorate.controller")
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public Map<String, String> handleValidationException(final NotFoundException e) {
        return Map.of("notFound", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public Map<String, String> handleValidationException(final ValidationException e) {
        return Map.of("validationError", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        return Map.of("validationError", e.getLocalizedMessage());
    }

}
