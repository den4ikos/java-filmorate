package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class FilmController {
    private FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films")
    public List<Film> findAll(HttpServletRequest request) {
        log.info("Endpoint request received: '{} {}', Query Parameter String: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        return filmService.findAll();
    }

    @GetMapping(value = "/films/{id}")
    public Film getById(@PathVariable Long id) {
        log.info("Endpoint request received: 'GET/films/{}'", id);
        return filmService.getById(id);
    }

    @PostMapping(value = "/films")
    public Film create(@Valid @RequestBody Film film) {
        log.info("Endpoint request received: 'POST/film {}'", film.toString());
        return filmService.create(film);
    }

    @PutMapping(value = "/films")
    public Film update(@Valid @RequestBody Film film) {
        log.info("Endpoint request received: 'POST/films/{} {}'", film.getId(), film.toString());
        return filmService.update(film);
    }

    @PutMapping(value = "/films/{id}/like/{userId}")
    public void addLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Endpoint request received: 'PUT/films/{}/like/{}'", id, userId);
        filmService.addLike(id, userId);
    }

    @DeleteMapping(value = "/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Endpoint request received: 'DELETE/films/{}/like/{}'", id, userId);
        filmService.deleteLike(id, userId);
    }

    @GetMapping(value = "/films/popular")
    public List<Film> get(@RequestParam(required = false) Map<String, String> params) {
        log.info("Endpoint request received: 'GET/films/popular, {}'", params.toString());
        return filmService.findByParams(params);
    }
}
