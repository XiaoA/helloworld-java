package com.example.helloworld.form;

import com.example.helloworld.account.Account;
import com.example.helloworld.account.AccountRepository;
import com.example.helloworld.account.AccountType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

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

		Form form = formService.createForm(account.getId(), new CreateFormRequestDto(
			FormType.F1234,
			"Gotham City Appeal"
		));

		assertThat(form.getFormTitle()).isEqualTo("Gotham City Appeal");
		assertThat(form.getFormType()).isEqualTo(FormType.F1234);
		assertThat(form.getId()).isNotNull();
		assertThat(form.getAccount()).isNotNull();
		assertThat(form.getAccount().getId()).isEqualTo(account.getId());
		assertThat(account.getForms()).extracting(Form::getId).contains(form.getId());
		assertThat(formRepository.findById(form.getId())).isPresent();
	}
}