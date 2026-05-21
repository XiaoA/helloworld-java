package com.example.helloworld.domain.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserAccountTest {

	@Test
	void addTaskKeepsBothSidesInSync() {
		UserAccount userAccount = new UserAccount();
		userAccount.setId(UUID.randomUUID());
		userAccount.setDisplayName("Bruce Wayne");
		userAccount.setEmail("batman@example.com");

		Task task = new Task();
		task.setTitle("Write docs");
		task.setStatus(TaskStatus.OPEN);

		userAccount.addTask(task);

		assertThat(userAccount.getTasks()).contains(task);
		assertThat(task.getUserAccount()).isSameAs(userAccount);
	}

	@Test
	void removeTaskClearsTheAssociation() {
		UserAccount userAccount = new UserAccount();
		userAccount.setId(UUID.randomUUID());
		userAccount.setDisplayName("Bruce Wayne");
		userAccount.setEmail("batman@example.com");

		Task task = new Task();
		task.setTitle("Write docs");
		task.setStatus(TaskStatus.OPEN);

		userAccount.addTask(task);
		userAccount.removeTask(task);

		assertThat(userAccount.getTasks()).doesNotContain(task);
		assertThat(task.getUserAccount()).isNull();
	}
}
