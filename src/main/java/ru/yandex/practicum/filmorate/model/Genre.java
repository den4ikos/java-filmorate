package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import javax.validation.constraints.*;

@Data
public class Genre {
    private Long id;

    @NotNull
    @NotBlank
    private String name;
}
