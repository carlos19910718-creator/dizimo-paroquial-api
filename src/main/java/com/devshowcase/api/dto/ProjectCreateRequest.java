package com.devshowcase.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record ProjectCreateRequest(
		@NotBlank(message = "O titulo e obrigatorio") String title,
		@NotBlank(message = "A descricao e obrigatoria") String description,
		@Pattern(regexp = "^$|https?://.+", message = "Informe uma URL valida") String projectUrl,
		@NotNull(message = "O perfil e obrigatorio") Long profileId,
		@NotEmpty(message = "Informe ao menos uma tecnologia") List<Long> technologyIds) {
}