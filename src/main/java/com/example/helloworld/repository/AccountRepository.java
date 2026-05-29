package com.example.helloworld.repository;

import com.example.helloworld.model.entity.Account;import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}



