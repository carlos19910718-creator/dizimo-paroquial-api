package com.devshowcase.api.controller;

import com.devshowcase.api.dto.ProfileCreateRequest;
import com.devshowcase.api.dto.ProfileResponse;
import com.devshowcase.api.model.Profile;
import com.devshowcase.api.repository.ProfileRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

	private final ProfileRepository profileRepository;

	public ProfileController(ProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}

	@PostMapping
	public ResponseEntity<ProfileResponse> create(@Valid @RequestBody ProfileCreateRequest request) {
		Profile profile = new Profile();
		profile.setName(request.name());
		profile.setEmail(request.email());
		profile.setBio(request.bio());
		profile.setGithubUrl(request.githubUrl());
		return ResponseEntity.status(HttpStatus.CREATED).body(ProfileResponse.from(profileRepository.save(profile)));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProfileResponse> findById(@PathVariable Long id) {
		return profileRepository.findById(id)
				.map(profile -> ResponseEntity.ok(ProfileResponse.from(profile)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
}