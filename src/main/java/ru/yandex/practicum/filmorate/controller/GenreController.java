package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping(value = "/genres/{id}")
    public Genre getGenreById(@PathVariable Long id) {
        log.info("Endpoint request received: 'GET/genres/{}'", id);
        return genreService.getById(id);
    }

    @GetMapping(value = "/genres")
    public List<Genre> getAll() {
        log.info("Endpoint request received: 'GET/genres/'");
        return genreService.getAll();
    }
}
