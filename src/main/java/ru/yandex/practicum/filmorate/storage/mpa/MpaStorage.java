package ru.yandex.practicum.filmorate.storage.mpa;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Map;

public interface MpaStorage {
    Mpa getById(Long id);
    Map<Long, Mpa> get();
}
