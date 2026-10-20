package com.example.todo.todo.repository.mysql;

import com.example.todo.todo.document.Todo;
import com.example.todo.todo.repository.TodoRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("mysql")
public class MySqlTodoRepository implements TodoRepository {
    private final JpaTodoRepository jpa;

    public MySqlTodoRepository(JpaTodoRepository jpa) { this.jpa = jpa; }
    public List<Todo> findAllByUserIdOrderByCreatedAtDesc(Long id) {
        return jpa.findAllByUserIdOrderByCreatedAtDesc(id).stream().map(JpaTodo::toTodo).toList();
    }
    public List<Todo> findAllByUserIdAndDoneOrderByCreatedAtDesc(Long id, boolean done) {
        return jpa.findAllByUserIdAndDoneOrderByCreatedAtDesc(id, done).stream().map(JpaTodo::toTodo).toList();
    }
    public Optional<Todo> findByIdAndUserId(String id, Long userId) {
        return jpa.findByIdAndUserId(id, userId).map(JpaTodo::toTodo);
    }
    public Todo save(Todo todo) { return jpa.save(JpaTodo.from(todo)).toTodo(); }
    public void delete(Todo todo) { jpa.deleteById(todo.getId()); }
}
