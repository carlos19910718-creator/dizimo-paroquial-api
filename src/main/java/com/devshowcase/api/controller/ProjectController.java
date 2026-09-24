package com.devshowcase.api.controller;

import com.devshowcase.api.dto.ProjectCreateRequest;
import com.devshowcase.api.dto.ProjectResponse;
import com.devshowcase.api.model.Project;
import com.devshowcase.api.repository.ProfileRepository;
import com.devshowcase.api.repository.ProjectRepository;
import com.devshowcase.api.repository.TechnologyRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

	private final ProjectRepository projectRepository;
	private final ProfileRepository profileRepository;
	private final TechnologyRepository technologyRepository;

	public ProjectController(ProjectRepository projectRepository, ProfileRepository profileRepository,
			TechnologyRepository technologyRepository) {
		this.projectRepository = projectRepository;
		this.profileRepository = profileRepository;
		this.technologyRepository = technologyRepository;
	}

	@PostMapping
	public ResponseEntity<ProjectResponse> create(@Valid @RequestBody ProjectCreateRequest request) {
		var profile = profileRepository.findById(request.profileId());
		var technologies = technologyRepository.findAllById(request.technologyIds());
		if (profile.isEmpty() || technologies.size() != new HashSet<>(request.technologyIds()).size()) {
			return ResponseEntity.notFound().build();
		}

		Project project = new Project();
		project.setTitle(request.title());
		project.setDescription(request.description());
		project.setProjectUrl(request.projectUrl());
		project.setProfile(profile.get());
		project.setTechnologies(technologies);
		return ResponseEntity.status(HttpStatus.CREATED).body(ProjectResponse.from(projectRepository.save(project)));
	}

	@GetMapping
	public List<ProjectResponse> findAll() {
		return projectRepository.findAll().stream().map(ProjectResponse::from).toList();
	}
}