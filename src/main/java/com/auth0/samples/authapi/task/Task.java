package com.auth0.samples.authapi.task;

import com.auth0.samples.authapi.user.ApplicationUser;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	private String description;

	@ManyToOne
	private ApplicationUser owner;

	protected Task() { }

	public Task(String description) {
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ApplicationUser getOwner() {
		return owner;
	}

	public void setOwner(ApplicationUser owner) {
		this.owner = owner;
	}
}
