package com.example.todo.todo.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface JpaTodoRepository extends JpaRepository<JpaTodo, String> {
    List<JpaTodo> findAllByUserIdOrderByCreatedAtDesc(Long userId);
    List<JpaTodo> findAllByUserIdAndDoneOrderByCreatedAtDesc(Long userId, boolean done);
    Optional<JpaTodo> findByIdAndUserId(String id, Long userId);
}
