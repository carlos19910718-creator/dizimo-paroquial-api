package com.devshowcase.api.controller;

import com.devshowcase.api.dto.TechnologyRequest;
import com.devshowcase.api.dto.TechnologyResponse;
import com.devshowcase.api.model.Technology;
import com.devshowcase.api.repository.TechnologyRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/technologies")
public class TechnologyController {

	private final TechnologyRepository technologyRepository;

	public TechnologyController(TechnologyRepository technologyRepository) {
		this.technologyRepository = technologyRepository;
	}

	@PostMapping
	public ResponseEntity<TechnologyResponse> create(@Valid @RequestBody TechnologyRequest request) {
		Technology technology = new Technology();
		technology.setName(request.name());
		Technology savedTechnology = technologyRepository.save(technology);
		return ResponseEntity.status(HttpStatus.CREATED).body(TechnologyResponse.from(savedTechnology));
	}

	@GetMapping
	public List<TechnologyResponse> findAll() {
		return technologyRepository.findAll().stream().map(TechnologyResponse::from).toList();
	}
}