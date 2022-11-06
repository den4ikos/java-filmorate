package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Qualifier("mpaDbStorage")
@RequiredArgsConstructor
public class MpaService {
    private final MpaStorage mpaStorage;

    public Mpa findById(Long id) {
        return mpaStorage.getById(id);
    }

    public List<Mpa> get() {
        List<Mpa> mpaList = new ArrayList<>();
        Map<Long, Mpa> mpaFromQuery = mpaStorage.get();
        if (!mpaFromQuery.isEmpty()) {
            mpaList.addAll(mpaFromQuery.values());
        }

         return mpaList;
    }
}
