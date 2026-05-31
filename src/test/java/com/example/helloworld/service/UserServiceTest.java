package com.example.helloworld.service;

import com.example.helloworld.exception.AccountNotFoundException;
import com.example.helloworld.exception.UserNotFoundException;
import com.example.helloworld.model.dto.CreateUserRequestDto;
import com.example.helloworld.model.dto.UpdateUserRequestDto;
import com.example.helloworld.model.entity.Account;
import com.example.helloworld.model.entity.User;
import com.example.helloworld.model.enums.AccountType;
import com.example.helloworld.model.enums.UserRole;
import com.example.helloworld.repository.AccountRepository;
import com.example.helloworld.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
	void listUsersReturnsUsersForAccount() {
		Account account = new Account();
		account.setDisplayName("Wayne Legal Group");
		account.setAccountType(AccountType.REPRESENTATIVE);
		account = accountRepository.save(account);

		User firstUser = userService.createUser(account.getId(), new CreateUserRequestDto(
			"Dick Grayson",
			"robin@waynelegal.com",
			UserRole.REPRESENTATIVE
		));

		User secondUser = userService.createUser(account.getId(), new CreateUserRequestDto(
			"Alfred",
			"alfred@waynelegal.com",
			UserRole.REGISTRANT
		));

		assertThat(userService.listUsers(account.getId()))
			.extracting(User::getId)
			.containsExactlyInAnyOrder(firstUser.getId(), secondUser.getId());
	}

	@Test
	void getUserReturnsUserForAccount() {
		Account account = new Account();
		account.setDisplayName("Wayne Legal Group");
		account.setAccountType(AccountType.REPRESENTATIVE);
		account = accountRepository.save(account);

		User user = userService.createUser(account.getId(), new CreateUserRequestDto(
			"Alfred",
			"alfred@waynelegal.com",
			UserRole.REGISTRANT
		));

		User foundUser = userService.getUser(account.getId(), user.getId());

		assertThat(foundUser.getId()).isEqualTo(user.getId());
		assertThat(foundUser.getDisplayName()).isEqualTo("Alfred");
		assertThat(foundUser.getEmail()).isEqualTo("alfred@waynelegal.com");
		assertThat(foundUser.getRole()).isEqualTo(UserRole.REGISTRANT);
		assertThat(foundUser.getAccount().getId()).isEqualTo(account.getId());

		assertThatThrownBy(() -> userService.getUser(UUID.randomUUID(), user.getId()))
			.isInstanceOf(UserNotFoundException.class);
	}

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

		assertThat(user.getAccount()).isNotNull();
		assertThat(user.getDisplayName()).isEqualTo("Bruce Wayne");
		assertThat(user.getEmail()).isEqualTo("batman@example.com");
		assertThat(user.getRole()).isEqualTo(UserRole.REPRESENTATIVE);
		assertThat(user.getAccount().getId()).isEqualTo(account.getId());

		User reloadedUser = userRepository.findById(user.getId()).orElseThrow();
		assertThat(reloadedUser.getAccount().getId()).isEqualTo(account.getId());
	}

	@Test
	void updateUserUpdatesUser() {
		Account account = new Account();
		account.setDisplayName("Wayne Legal Group");
		account.setAccountType(AccountType.REPRESENTATIVE);
		account = accountRepository.save(account);

		User user = userService.createUser(account.getId(), new CreateUserRequestDto(
			"Bruce Wayne",
			"batman@example.com",
			UserRole.REPRESENTATIVE
		));

		User updatedUser = userService.updateUser(account.getId(), user.getId(), new UpdateUserRequestDto(
			"Clark Kent",
			"superman@example.com",
			UserRole.REGISTRANT
		));

		assertThat(updatedUser.getId()).isEqualTo(user.getId());
		assertThat(updatedUser.getDisplayName()).isEqualTo("Clark Kent");
		assertThat(updatedUser.getEmail()).isEqualTo("superman@example.com");
		assertThat(updatedUser.getRole()).isEqualTo(UserRole.REGISTRANT);
		assertThat(updatedUser.getAccount().getId()).isEqualTo(account.getId());

		// Verify that the database has persisted the changes
		User reloadedUser = userRepository.findById(user.getId()).orElseThrow();

		assertThat(reloadedUser.getId()).isEqualTo(user.getId());
		assertThat(reloadedUser.getDisplayName()).isEqualTo("Clark Kent");
		assertThat(reloadedUser.getEmail()).isEqualTo("superman@example.com");
		assertThat(reloadedUser.getRole()).isEqualTo(UserRole.REGISTRANT);
		assertThat(reloadedUser.getAccount().getId()).isEqualTo(account.getId());
	}

	@Test
	void updateUserThrowsNotFoundWhenUserDoesNotBelongToAccount() {
		Account account = new Account();
		account.setDisplayName("Wayne Legal Group");
		account.setAccountType(AccountType.REPRESENTATIVE);
		account = accountRepository.save(account);

		Account otherAccount = new Account();
		otherAccount.setDisplayName("Daily Planet");
		otherAccount.setAccountType(AccountType.REPRESENTATIVE);
		Account savedOtherAccount = accountRepository.save(otherAccount);

		User user = userService.createUser(account.getId(), new CreateUserRequestDto(
			"Bruce Wayne",
			"batman@example.com",
			UserRole.REPRESENTATIVE
		));

		assertThatThrownBy(() -> userService.updateUser(savedOtherAccount.getId(), user.getId(), new UpdateUserRequestDto(
			"Clark Kent",
			"superman@example.com",
			UserRole.REGISTRANT
		)))
			.isInstanceOf(UserNotFoundException.class);
	}

	@Test
	void updateUserThrowsNotFoundWhenAccountDoesNotExist() {
		Account account = new Account();
		account.setDisplayName("Wayne Legal Group");
		account.setAccountType(AccountType.REPRESENTATIVE);
		account = accountRepository.save(account);

		User user = userService.createUser(account.getId(), new CreateUserRequestDto(
			"Bruce Wayne",
			"batman@example.com",
			UserRole.REPRESENTATIVE
		));

		assertThatThrownBy(() -> userService.updateUser(
			UUID.randomUUID(),
			user.getId(),
			new UpdateUserRequestDto("Clark Kent", "superman@example.com", UserRole.REGISTRANT)
		))
			.isInstanceOf(AccountNotFoundException.class);
	}

	@Test
	void deleteUserRemovesUserFromAccount() {
		Account account = new Account();
		account.setDisplayName("Wayne Legal Group");
		account.setAccountType(AccountType.REPRESENTATIVE);
		account = accountRepository.save(account);

		User user = userService.createUser(account.getId(), new CreateUserRequestDto(
			"Bruce Wayne",
			"batman@example.com",
			UserRole.REPRESENTATIVE
		));

		UUID accountId = account.getId();
		userService.deleteUser(accountId, user.getId());
		userRepository.flush();

		assertThat(userRepository.findById(user.getId())).isEmpty();
	}


}
