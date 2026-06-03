package com.example.helloworld.repository;

import com.example.helloworld.model.entity.Addendum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AddendumRepository extends JpaRepository<Addendum, UUID> {
}
