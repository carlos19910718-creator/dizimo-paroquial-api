package com.devshowcase.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "feedbacks")
public class Feedback {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String author;

	@Column(nullable = false, length = 1000)
	private String comment;

	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;

	public Feedback() {
	}

	public Long getId() {
		return id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}