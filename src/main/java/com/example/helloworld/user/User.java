package com.example.helloworld.user;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID id;

	// displayName
	// email
	// role

	@Column(name = "displayName", nullable = false)
	private String displayName;

	@Column(name = "email", nullable = false)
	private String email;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private UserRole role;
}
