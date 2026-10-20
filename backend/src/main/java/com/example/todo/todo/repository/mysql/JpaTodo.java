package com.example.todo.todo.repository.mysql;

import com.example.todo.todo.document.Todo;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "todos")
public class JpaTodo {
    @Id
    @Column(length = 36)
    private String id;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private boolean done;
    @Column(nullable = false)
    private Instant createdAt;
    @Column(nullable = false)
    private Instant updatedAt;

    protected JpaTodo() {}

    static JpaTodo from(Todo todo) {
        JpaTodo entity = new JpaTodo();
        entity.id = todo.getId() == null ? UUID.randomUUID().toString() : todo.getId();
        entity.userId = todo.getUserId();
        entity.text = todo.getText();
        entity.done = todo.isDone();
        entity.createdAt = todo.getCreatedAt();
        entity.updatedAt = todo.getUpdatedAt();
        return entity;
    }

    Todo toTodo() { return new Todo(id, userId, text, done, createdAt, updatedAt); }
}
