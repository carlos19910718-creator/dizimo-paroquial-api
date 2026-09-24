package com.devshowcase.api.dto;

import jakarta.validation.constraints.NotBlank;

public record TechnologyRequest(@NotBlank(message = "O nome da tecnologia e obrigatorio") String name) {
}