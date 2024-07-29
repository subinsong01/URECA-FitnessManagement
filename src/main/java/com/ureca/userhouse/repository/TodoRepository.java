package com.ureca.userhouse.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ureca.userhouse.entity.Manager;
import com.ureca.userhouse.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByManager(Manager manager);
    Optional<Todo> findByIdAndManager(Long id, Manager manager);
}