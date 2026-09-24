package com.devshowcase.api.dto;

import com.devshowcase.api.model.Profile;

import java.util.List;

public record ProfileResponse(Long id, String name, String email, String bio, String githubUrl,
		List<ProjectSummary> projects) {

	public static ProfileResponse from(Profile profile) {
		List<ProjectSummary> projects = profile.getProjects().stream()
				.map(project -> new ProjectSummary(project.getId(), project.getTitle()))
				.toList();
		return new ProfileResponse(profile.getId(), profile.getName(), profile.getEmail(), profile.getBio(),
				profile.getGithubUrl(), projects);
	}

	public record ProjectSummary(Long id, String title) {
	}
}