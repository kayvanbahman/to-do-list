package com.example.todo.auth.repository.mongo;

import com.example.todo.auth.entity.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Document("users")
public class MongoUser {
    @Id
    private Long id;
    @Indexed(unique = true)
    private String email;
    private String passwordHash;
    private Instant createdAt;

    public MongoUser() {}
    public MongoUser(Long id, String email, String passwordHash, Instant createdAt) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
    }
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public Instant getCreatedAt() { return createdAt; }
    public User toUser() { return new User(id, email, passwordHash, createdAt); }
}
