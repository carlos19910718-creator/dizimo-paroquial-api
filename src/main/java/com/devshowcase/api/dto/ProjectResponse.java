package com.devshowcase.api.dto;

import com.devshowcase.api.model.Project;

import java.util.List;

public record ProjectResponse(Long id, String title, String description, String projectUrl, Long profileId,
		List<TechnologyResponse> technologies) {

	public static ProjectResponse from(Project project) {
		return new ProjectResponse(project.getId(), project.getTitle(), project.getDescription(), project.getProjectUrl(),
				project.getProfile().getId(), project.getTechnologies().stream().map(TechnologyResponse::from).toList());
	}
}