package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
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
}
