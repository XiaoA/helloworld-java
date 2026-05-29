package com.example.helloworld.model.entity;

import com.example.helloworld.model.enums.AccountType;
import com.example.helloworld.model.enums.FormType;
import com.example.helloworld.model.enums.UserRole;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AccountTest {

	@Test
	void addUserKeepsAssociationInSync() {
		Account account = new Account();
		account.setId(UUID.randomUUID());
		account.setDisplayName("Wayne Legal Group");
		account.setAccountType(AccountType.REPRESENTATIVE);

		User user = new User();
		user.setId(UUID.randomUUID());
		user.setDisplayName("Bruce Wayne");
		user.setEmail("batman@example.com");
		user.setRole(UserRole.REPRESENTATIVE);

		account.addUser(user);

		assertThat(account.getUsers()).contains(user);
		assertThat(user.getAccount()).isSameAs(account);
	}

	@Test
	void removeUserClearsTheAssociation() {
		Account account = new Account();
		account.setId(UUID.randomUUID());
		account.setDisplayName("Wayne Legal Group");
		account.setAccountType(AccountType.REPRESENTATIVE);

		User user = new User();
		user.setId(UUID.randomUUID());
		user.setDisplayName("Bruce Wayne");
		user.setEmail("batman@example.com");
		user.setRole(UserRole.REPRESENTATIVE);

		account.addUser(user);
		account.removeUser(user);

		assertThat(account.getUsers()).doesNotContain(user);
		assertThat(user.getAccount()).isNull();
	}

	@Test
	void addFormKeepsAssociationInSync() {
		Account account = new Account();
		account.setId(UUID.randomUUID());
		account.setDisplayName("Wayne Legal Group");
		account.setAccountType(AccountType.REPRESENTATIVE);

		Form form = new Form();
		form.setId(UUID.randomUUID());
		form.setFormTitle("Gotham City Formal Appeal");
		form.setFormType(FormType.F1234);

		account.addForm(form);

		assertThat(form.getAccount()).isSameAs(account);
		assertThat(account.getForms()).contains(form);
	}

	@Test
	void removeFormClearsTheAssociation() {
		Account account = new Account();
		account.setId(UUID.randomUUID());
		account.setDisplayName("Wayne Legal Group");
		account.setAccountType(AccountType.REPRESENTATIVE);

		Form form = new Form();
		form.setId(UUID.randomUUID());
		form.setFormTitle("Gotham City Formal Appeal");
		form.setFormType(FormType.F1234);

		account.addForm(form);
		account.removeForm(form);

		assertThat(account.getForms()).doesNotContain(form);
		assertThat(form.getAccount()).isNull();
	}

}