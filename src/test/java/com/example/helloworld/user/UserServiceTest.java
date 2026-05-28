package com.example.helloworld.user;

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
class UserServiceTest {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Test
	void createUserAddsUserToExistingAccount() {
		Account account = new Account();
		account.setDisplayName("Bruce Wayne");
		account.setAccountType(AccountType.REPRESENTATIVE);
		account = accountRepository.save(account);

		User user = userService.createUser(account.getId(), new CreateUserRequestDto(
			"Bruce Wayne",
			"batman@example.com",
			UserRole.REPRESENTATIVE
		));

		assertThat(user.getDisplayName()).isEqualTo("Bruce Wayne");
		assertThat(user.getEmail()).isEqualTo("batman@example.com");
		assertThat(user.getRole()).isEqualTo(UserRole.REPRESENTATIVE);
		assertThat(user.getAccount()).isNotNull();
		assertThat(user.getAccount().getId()).isEqualTo(account.getId());
		assertThat(account.getUsers()).extracting(User::getId).contains(user.getId());
		assertThat(userRepository.findById(user.getId())).isPresent();
	}
}
