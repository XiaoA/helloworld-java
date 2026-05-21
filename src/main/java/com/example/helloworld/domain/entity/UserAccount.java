package com.example.helloworld.domain.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_accounts")
public class UserAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private String displayName;

	@Column(nullable = false, unique = true)
	private String email;

	@OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Task> tasks = new ArrayList<>();

	public void addTask(Task task) {
		tasks.add(task);
		task.setUserAccount(this);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public void removeTask(Task task) {
		tasks.remove(task);
		task.setUserAccount(null);
	}
}

