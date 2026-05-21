package com.example.helloworld.repository;

import com.example.helloworld.domain.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {
}
