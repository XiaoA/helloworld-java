package com.example.helloworld.form;

import com.example.helloworld.account.Account;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "forms")
public class Form {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	// In a real-world application, I'd probably make this a polymorphic relationship.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	@Enumerated(EnumType.STRING)
	@Column(name = "form_type")
	private FormType formType;

	@Column(name = "form_title")
	private String formTitle;

	public Form() {
	}

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

	public FormType getFormType() {
		return formType;
	}

	public void setFormType(FormType formType) {
		this.formType = formType;
	}

	public String getFormTitle() {
		return formTitle;
	}

	public void setFormTitle(String formTitle) {
		this.formTitle = formTitle;
	}
}
