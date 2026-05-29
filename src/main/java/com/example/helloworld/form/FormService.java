package com.example.helloworld.form;

import com.example.helloworld.account.Account;
import com.example.helloworld.account.AccountNotFoundException;
import com.example.helloworld.account.AccountRepository;
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
		form.setAccount(account);
		form.setFormType(request.formType());
		form.setFormTitle(request.formTitle());

		return formRepository.save(form);
	}

	// List Forms
	public List<Form> listForms(UUID accountId) {
		getAccount(accountId);
		return formRepository.findAllByAccountId(accountId);
	}

	// Get Form by ID
	public Form getForm(UUID accountId, UUID formId) {
		getAccount(accountId);
		return formRepository.findByIdAndAccountId(formId, accountId)
			.orElseThrow(() -> new FormNotFoundException(formId));
	}

	// Update Form
	public Form updateForm(UUID accountId, UUID formId, UpdateFormRequestDto request) {
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
