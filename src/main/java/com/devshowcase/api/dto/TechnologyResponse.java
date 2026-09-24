package com.devshowcase.api.dto;

import com.devshowcase.api.model.Technology;

public record TechnologyResponse(Long id, String name) {

	public static TechnologyResponse from(Technology technology) {
		return new TechnologyResponse(technology.getId(), technology.getName());
	}
}