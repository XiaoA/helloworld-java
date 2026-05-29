package com.example.helloworld.model.entity;

import com.example.helloworld.model.enums.AccountType;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<User> users = new ArrayList<>();

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Form> forms = new ArrayList<>();

	@Column(name = "display_name", nullable = false)
	private String displayName;

	@Enumerated(EnumType.STRING)
	@Column(name = "account_type", nullable = false)
	private AccountType accountType;

	@Column(name = "created_at", updatable = false, nullable = false)
	private Instant createdAt;

	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;

	@PrePersist
	void prePersist() {
		Instant now = Instant.now();
		if (createdAt == null) {
			createdAt = now;
		}
		updatedAt = now;
	}

	@PreUpdate
	void preUpdate() {
		updatedAt = Instant.now();
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

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<User> getUsers() {
		return users;
	}

	public void addUser(User user) {
		users.add(user);
		user.setAccount(this);
	}

	public void removeUser(User user) {
		users.remove(user);
		if (user.getAccount() == this) {
			user.setAccount(null);
		}
	}

	public void addForm(Form form) {
		forms.add(form);
		form.setAccount(this);
	}

	public List<Form> getForms() {
		return forms;
	}

	public void removeForm(Form form) {
		forms.remove(form);
		if (form.getAccount() == this) {
			form.setAccount(null);
		}
	}
}
