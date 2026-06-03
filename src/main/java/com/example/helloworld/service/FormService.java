package com.example.helloworld.service;

import com.example.helloworld.exception.FormNotFoundException;
import com.example.helloworld.model.dto.CreateFormRequestDto;
import com.example.helloworld.model.dto.UpdateFormRequestDto;
import com.example.helloworld.exception.AccountNotFoundException;
import com.example.helloworld.model.entity.Account;
import com.example.helloworld.repository.AccountRepository;
import com.example.helloworld.model.entity.Form;
import com.example.helloworld.repository.FormRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FormService {
	private final FormRepository formRepository;
	private final AccountRepository accountRepository;

	public FormService(FormRepository formRepository, AccountRepository accountRepository) {
		this.formRepository = formRepository;
		this.accountRepository = accountRepository;
	}

	// Create Form
	@Transactional
	public Form createForm(UUID accountId, CreateFormRequestDto request) {
		Account account = getAccount(accountId);
		Form form = new Form();
		form.setFormType(request.formType());
		form.setFormTitle(request.formTitle());
		account.addForm(form);

		return formRepository.save(form);
	}

	// List Forms
	public List<Form> listForms(UUID accountId) {
		getAccount(accountId);
		return formRepository.findAllByAccountId(accountId);
	}

	// Get Form by ID
	public Form getForm(UUID accountId, UUID formId) {
		return formRepository.findByIdAndAccountId(formId, accountId)
			.orElseThrow(() -> new FormNotFoundException(formId));
	}

	// Update Form
	public Form updateForm(UUID accountId, UUID formId, UpdateFormRequestDto request) {
		getAccount(accountId);
		Form form = getForm(accountId, formId);

		form.setFormType(request.formType());
		form.setFormTitle(request.formTitle());

		return formRepository.save(form);
	}

	private Account getAccount(UUID accountId) {
		return accountRepository.findById(accountId)
			.orElseThrow(() -> new AccountNotFoundException(accountId));
	}
}
