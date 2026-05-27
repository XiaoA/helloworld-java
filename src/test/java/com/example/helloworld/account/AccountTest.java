package com.example.helloworld.account;

import com.example.helloworld.user.User;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AccountTest {

	@Test
	void addAccountKeepsAssociationInSync() {
		User user = new User();
		user.setId(UUID.randomUUID());
		user.setDisplayName("Bruce Wayne");
		user.setEmail("batman@example.com");

		Account account = new Account();
		account.setId(UUID.randomUUID());
		account.setAccountType(AccountType.valueOf("REPRESENTATIVE"));

		user.addAccount(account);

		assertThat(user.hasAccount()).isTrue();
		assertThat(user.getAccount()).isSameAs(account);
	}

	@Test
	void removeAccountClearsTheAssociation() {
		User user = new User();
		user.setId(UUID.randomUUID());
		user.setDisplayName("Bruce Wayne");
		user.setEmail("batman@example.com");

		Account account = new Account();
		account.setId(UUID.randomUUID());
		account.setAccountType(AccountType.valueOf("REPRESENTATIVE"));

		user.addAccount(account);
		user.removeAccount(account);

		assertThat(user.hasAccount()).isFalse();
		assertThat(user.getAccount()).isNull();
	}
}
