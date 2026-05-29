package com.example.helloworld.repository;

import com.example.helloworld.model.entity.User;import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
	List<User> findAllByAccountId(UUID accountId);

	Optional<User> findByIdAndAccountId(UUID userId, UUID accountId);
}
