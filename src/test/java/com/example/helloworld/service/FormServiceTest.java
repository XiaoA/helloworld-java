package com.example.helloworld.service;

import com.example.helloworld.exception.AccountNotFoundException;
import com.example.helloworld.exception.FormNotFoundException;
import com.example.helloworld.model.dto.CreateFormRequestDto;
import com.example.helloworld.model.dto.UpdateFormRequestDto;
import com.example.helloworld.model.entity.Account;
import com.example.helloworld.repository.AccountRepository;
import com.example.helloworld.model.enums.AccountType;
import com.example.helloworld.model.entity.Form;
import com.example.helloworld.model.enums.FormType;
import com.example.helloworld.repository.FormRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("h2")
@Transactional
class FormServiceTest {

	@Autowired
	private FormService formService;

	@Autowired
	private FormRepository formRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Test
	void listFormsReturnsFormsForAccount() {
		Account account = new Account();
		account.setDisplayName("Wayne Legal Group");
		account.setAccountType(AccountType.REPRESENTATIVE);
		account = accountRepository.save(account);

		Form firstForm = formService.createForm(account.getId(), new CreateFormRequestDto(
			FormType.F1234,
			"Gotham City Form"
		));

		Form secondForm = formService.createForm(account.getId(), new CreateFormRequestDto(
			FormType.F200,
			"Gotham City Appeal"
		));

		assertThat(formService.listForms(account.getId()))
			.extracting(Form::getId)
			.containsExactlyInAnyOrder(firstForm.getId(), secondForm.getId());
	}

	@Test
	void getFormReturnsFormForAccount() {
		Account account = new Account();
		account.setDisplayName("Wayne Legal Group");
		account.setAccountType(AccountType.REPRESENTATIVE);
		account = accountRepository.save(account);

		Form form = formService.createForm(account.getId(), new CreateFormRequestDto(
			FormType.F1234,
			"Gotham City Form"
		));

		Form foundForm = formService.getForm(account.getId(), form.getId());

		assertThat(foundForm.getId()).isEqualTo(form.getId());
		assertThat(foundForm.getFormType()).isEqualTo(FormType.F1234);
		assertThat(foundForm.getFormTitle()).isEqualTo("Gotham City Form");
		assertThat(foundForm.getAccount().getId()).isEqualTo(account.getId());

		assertThatThrownBy(() -> formService.getForm(UUID.randomUUID(), form.getId()))
			.isInstanceOf(FormNotFoundException.class);
	}

	@Test
	void createFormAddsFormToExistingAccount() {
		Account account = new Account();
		account.setDisplayName("Wayne Legal Group");
		account.setAccountType(AccountType.REPRESENTATIVE);
		account = accountRepository.save(account);

		Form form = formService.createForm(
			account.getId(),
			new CreateFormRequestDto(
				FormType.F1234,
				"Gotham City Appeal"
			)
		);

		assertThat(form.getFormTitle()).isEqualTo("Gotham City Appeal");
		assertThat(form.getFormType()).isEqualTo(FormType.F1234);
		assertThat(form.getId()).isNotNull();
		assertThat(form.getAccount()).isNotNull();
		assertThat(form.getAccount().getId()).isEqualTo(account.getId());
		assertThat(account.getForms()).extracting(Form::getId).contains(form.getId());
		assertThat(formRepository.findById(form.getId())).isPresent();
	}

	@Test
	void updateFormUpdatesForm() {
		Account account = new Account();
		account.setDisplayName("Wayne Legal Group");
		account.setAccountType(AccountType.REPRESENTATIVE);
		account = accountRepository.save(account);

		Form form = formService.createForm(account.getId(), new CreateFormRequestDto(
			FormType.F1234,
			"Gotham City Form"
		));

		Form updatedForm = formService.updateForm(account.getId(), form.getId(), new UpdateFormRequestDto(
			FormType.F200,
			"Gotham City Appeal"
		));

		assertThat(updatedForm.getId()).isEqualTo(form.getId());
		assertThat(updatedForm.getFormType()).isEqualTo(FormType.F200);
		assertThat(updatedForm.getFormTitle()).isEqualTo("Gotham City Appeal");
		assertThat(updatedForm.getAccount().getId()).isEqualTo(account.getId());

		// Verify that the database has persisted the changes
		Form reloadedForm = formRepository.findById(form.getId()).orElseThrow();

		assertThat(reloadedForm.getId()).isEqualTo(form.getId());
		assertThat(reloadedForm.getFormType()).isEqualTo(FormType.F200);
		assertThat(reloadedForm.getFormTitle()).isEqualTo("Gotham City Appeal");
		assertThat(reloadedForm.getAccount().getId()).isEqualTo(account.getId());
	}

	@Test
	void updateFormThrowsNotFoundWhenFormDoesNotBelongToAccount() {
		Account account = new Account();
		account.setDisplayName("Wayne Legal Group");
		account.setAccountType(AccountType.REPRESENTATIVE);
		account = accountRepository.save(account);

		Account otherAccount = new Account();
		otherAccount.setDisplayName("Daily Planet");
		otherAccount.setAccountType(AccountType.REPRESENTATIVE);
		Account savedOtherAccount = accountRepository.save(otherAccount);

		Form form = formService.createForm(account.getId(), new CreateFormRequestDto(
			FormType.F1234,
			"Gotham City Form"
		));

		assertThatThrownBy(() -> formService.updateForm(savedOtherAccount.getId(), form.getId(), new UpdateFormRequestDto(
			FormType.F200,
			"Gotham City Appeal"
		)))
			.isInstanceOf(FormNotFoundException.class);
	}

	@Test
	void updateFormThrowsNotFoundWhenAccountDoesNotExist() {
		Account account = new Account();
		account.setDisplayName("Wayne Legal Group");
		account.setAccountType(AccountType.REPRESENTATIVE);
		account = accountRepository.save(account);

		Form form = formService.createForm(account.getId(), new CreateFormRequestDto(
			FormType.F1234,
			"Gotham City Form"
		));

		assertThatThrownBy(() -> formService.updateForm(
			UUID.randomUUID(),
			form.getId(),
			new UpdateFormRequestDto(
				FormType.F200,
				"Gotham City Appeal"
			)))
			.isInstanceOf(AccountNotFoundException.class);
	}
}
