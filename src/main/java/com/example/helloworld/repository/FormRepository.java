package com.example.helloworld.repository;

import com.example.helloworld.model.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FormRepository extends JpaRepository<Form, UUID> {
	List<Form> findAllByAccountId(UUID accountId);

	Optional<Form> findByIdAndAccountId(UUID formId, UUID accountId);
}
