package com.example.helloworld.service.impl;

import com.example.helloworld.domain.CreateTaskRequest;
import com.example.helloworld.domain.entity.Task;
import com.example.helloworld.domain.entity.TaskStatus;
import com.example.helloworld.domain.entity.UserAccount;
import com.example.helloworld.exception.UserAccountNotFoundException;
import com.example.helloworld.repository.TaskRepository;
import com.example.helloworld.repository.UserAccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Disabled;

@Disabled("Old task spike tests; superseded by Account/User MVP branch")
@SpringBootTest
@Transactional
class TaskServiceImplTest {

	@Autowired
	private TaskServiceImpl taskService;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Test
	void createTaskAssociatesTheTaskWithTheUserAccount() {
		UserAccount userAccount = new UserAccount();
		userAccount.setDisplayName("Bruce Wayne");
		userAccount.setEmail("batman@example.com");
		userAccount = userAccountRepository.save(userAccount);

		Task createdTask = taskService.createTask(
			new CreateTaskRequest(
				userAccount.getId(),
				"Test task",
				"Created from test",
				LocalDate.now()
			)
		);

		assertThat(createdTask.getUserAccount()).isNotNull();
		assertThat(createdTask.getUserAccount().getId()).isEqualTo(userAccount.getId());
		assertThat(createdTask.getStatus()).isEqualTo(TaskStatus.OPEN);
		assertThat(createdTask.getTitle()).isEqualTo("Test task");

		Task reloadedTask = taskRepository.findById(createdTask.getId()).orElseThrow();
		assertThat(reloadedTask.getUserAccount().getId()).isEqualTo(userAccount.getId());
	}

	@Test
	void createTaskThrowsWhenUserAccountDoesNotExist() {
		UUID userAccountId = UUID.randomUUID();

		assertThatThrownBy(() -> taskService.createTask(
			new CreateTaskRequest(
				userAccountId,
				"Test task",
				"Created from test",
				LocalDate.now()
			)
		)).isInstanceOf(UserAccountNotFoundException.class);
	}
}
