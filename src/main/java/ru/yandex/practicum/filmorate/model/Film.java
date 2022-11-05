package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class Film {
    private Long id;
    @NotNull
    @NotBlank(message = "Name is required!")
    private String name;

    @NotNull
    @NotBlank(message = "Description is required!")
    @Size(max = 200, message = "Max length 200 symbols")
    private String description;

    @NotNull
    @Past
    private LocalDate releaseDate;

    @NotNull
    @Positive(message = "Duration must be positive value!")
    private Integer duration;

    @NotNull
    @Positive
    private Integer rate;

    private Set<Long> likes = new HashSet<>();

    private Map<String, Integer> mpa = new HashMap<>();

    public void addLike(Long userId) {
        likes.add(userId);
    }
}
