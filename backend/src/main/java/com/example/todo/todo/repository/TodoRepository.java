package com.example.todo.todo.repository;

import com.example.todo.todo.document.Todo;
import java.util.List;
import java.util.Optional;

public interface TodoRepository {
    List<Todo> findAllByUserIdOrderByCreatedAtDesc(Long userId);

    List<Todo> findAllByUserIdAndDoneOrderByCreatedAtDesc(Long userId, boolean done);

    Optional<Todo> findByIdAndUserId(String id, Long userId);
    Todo save(Todo todo);
    void delete(Todo todo);
}
