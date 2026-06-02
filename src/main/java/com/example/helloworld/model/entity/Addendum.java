package com.example.helloworld.model.entity;

import com.example.helloworld.model.enums.AddendumType;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "addenda")
public class Addendum {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "addendum_title", nullable = false)
	private String addendumTitle;

	@Column(name = "addendum_type", nullable = false)
	private AddendumType addendumType;

@Column(name = "addendum_text", nullable = false)
private String addendumText;



	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getAddendumTitle() {
		return addendumTitle;
	}

	public void setAddendumTitle(String addendumTitle) {
		this.addendumTitle = addendumTitle;
	}

	public String getAddendumText() {
		return addendumText;
	}

	public void setAddendumText(String addendumText) {
		this.addendumText = addendumText;
	}

	public AddendumType getAddendumType() {
		return addendumType;
	}

	public void setAddendumType(AddendumType addendumType) {
	}
}
