package com.example.helloworld.service;

import com.example.helloworld.exception.FormNotFoundException;
import com.example.helloworld.model.dto.CreateAddendumRequestDto;
import com.example.helloworld.model.entity.Addendum;
import com.example.helloworld.model.entity.Account;
import com.example.helloworld.model.entity.Form;
import com.example.helloworld.model.enums.AccountType;
import com.example.helloworld.model.enums.FormType;
import com.example.helloworld.repository.AddendumRepository;
import com.example.helloworld.repository.AccountRepository;
import com.example.helloworld.repository.FormRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static com.example.helloworld.model.enums.AddendumType.UPDATE_STATEMENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("h2")
@Transactional
class AddendumServiceTest {

	@Autowired
	private AddendumService addendumService;

	@Autowired
	private AddendumRepository addendumRepository;

	@Autowired
	private FormRepository formRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Test
	void createAddendumAddsAddendumToExistingForm() {
		Account account = new Account();
		account.setDisplayName("Wayne Legal Group");
		account.setAccountType(AccountType.REPRESENTATIVE);
		account = accountRepository.save(account);

		Form form = new Form();
		form.setFormTitle("Gotham City Appeal");
		form.setFormType(FormType.F1234);
		account.addForm(form);
		form = formRepository.save(form);

		Addendum addendum = addendumService.createAddendum(
			form.getId(),
			new CreateAddendumRequestDto(
				"Test Addendum Text",
				UPDATE_STATEMENT,
				"Test Addendum Title"
			)
		);

		Addendum reloadedAddendum = addendumRepository.findById(addendum.getId()).orElseThrow();

		assertThat(addendum.getId()).isNotNull();
		assertThat(addendum.getAddendumType()).isEqualTo(UPDATE_STATEMENT);
		assertThat(addendum.getAddendumTitle()).isEqualTo("Test Addendum Title");
		assertThat(addendum.getAddendumText()).isEqualTo("Test Addendum Text");
		assertThat(addendum.getForm().getId()).isEqualTo(form.getId());

		assertThat(reloadedAddendum.getForm().getId()).isEqualTo(form.getId());
	}

	@Test
	void createAddendumThrowsWhenFormDoesNotExist() {
		assertThatThrownBy(() -> addendumService.createAddendum(
			UUID.randomUUID(),
			new CreateAddendumRequestDto(
				"Test Addendum Text",
				UPDATE_STATEMENT,
				"Test Addendum Title"
			)
		))
			.isInstanceOf(FormNotFoundException.class);
	}
}
