package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MpaController {
    private final MpaService mpaService;

    @GetMapping("/mpa/{id}")
    public Mpa getMpaById(@PathVariable Long id) {
        log.info("Endpoint request received: 'GET /mpa/{}'", id);
        return mpaService.findById(id);
    }

    @GetMapping("/mpa")
    public List<Mpa> getAll() {
        log.info("Endpoint request received: 'GET /mpa");
        return mpaService.get();
    }
}
