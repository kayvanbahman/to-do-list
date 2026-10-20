package com.example.todo.todo.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("todos")
public class Todo {
    @Id
    private String id;
    private Long userId;
    private String text;
    private boolean done;
    private Instant createdAt;
    private Instant updatedAt;

    public Todo() {
    }

    public Todo(Long userId, String text) {
        this.userId = userId;
        this.text = text;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    public Todo(String id, Long userId, String text, boolean done, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.userId = userId;
        this.text = text;
        this.done = done;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getText() {
        return text;
    }

    public boolean isDone() {
        return done;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void update(String text, Boolean done) {
        if (text != null)
            this.text = text;
        if (done != null)
            this.done = done;
        this.updatedAt = Instant.now();
    }
}
