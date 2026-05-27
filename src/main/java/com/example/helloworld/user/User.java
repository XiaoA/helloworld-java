package com.example.helloworld.user;

import com.example.helloworld.account.Account;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	private Account account;

	@Column(name = "displayName", nullable = false)
	private String displayName;

	@Column(name = "email", nullable = false)
	private String email;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private UserRole role;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
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

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public void addAccount(Account account) {
		this.account = account;
	}

	public boolean hasAccount() {
		return account != null;
	}

	public void removeAccount(Account account) {
		if (this.account == account) {
			this.account = null;
		}
	}
}
