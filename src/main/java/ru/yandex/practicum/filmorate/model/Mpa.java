package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import javax.validation.constraints.*;

@Data
public class Mpa {
    private Long id;

    @NotNull
    @NotBlank
    private String name;

    private String shortDescription;
}
