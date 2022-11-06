package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;

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

    private Integer rate;

    @NotNull(message = "MPA is required")
    private Map<String, Object> mpa = new LinkedHashMap<>();

    private List<Map<String, Object>> genres = new ArrayList<>();

    private Set<Long> likes = new HashSet<>();

    public void addLike(Long userId) {
        likes.add(userId);
    }
}
