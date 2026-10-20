package com.example.todo.todo.repository.mongo;

import com.example.todo.todo.document.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface SpringMongoTodoRepository extends MongoRepository<Todo, String> {
    List<Todo> findAllByUserIdOrderByCreatedAtDesc(Long userId);
    List<Todo> findAllByUserIdAndDoneOrderByCreatedAtDesc(Long userId, boolean done);
    Optional<Todo> findByIdAndUserId(String id, Long userId);
}
