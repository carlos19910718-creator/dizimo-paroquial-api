package com.devshowcase.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ProfileCreateRequest(
		@NotBlank(message = "O nome e obrigatorio") String name,
		@NotBlank(message = "O e-mail e obrigatorio") @Email(message = "Informe um e-mail valido") String email,
		String bio,
		@Pattern(regexp = "^$|https?://.+", message = "Informe uma URL valida") String githubUrl) {
}