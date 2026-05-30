package com.example.helloworld.service;

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
			"Gotham City Appeal"
		));

		assertThat(form.getFormTitle()).isEqualTo("Gotham City Appeal");
		assertThat(form.getFormType()).isEqualTo(FormType.F1234);

		formService.updateForm(account.getId(), form.getId(), new UpdateFormRequestDto(
			FormType.F200,
			"Updated Gotham City Appeal"
		));

		Form updatedForm = formRepository.findById(form.getId()).orElseThrow();

		assertThat(updatedForm.getFormType()).isEqualTo(FormType.F200);
		assertThat(updatedForm.getFormTitle()).isEqualTo("Updated Gotham City Appeal");
	}

//	@Test
//	void getFormReturnsForm() {
//		UUID uuid = UUID.randomUUID();
//		Form form = FormRepository.findById(uuid).orElseThrow();
//		assertThat(form).isNotNull();
//	}
}