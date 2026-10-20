package com.example.todo.auth.repository.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Document("refresh_tokens")
public class MongoRefreshToken {
    @Id
    private String tokenHash;
    private Long userId;
    private Instant expiresAt;
    private boolean revoked;

    public MongoRefreshToken() {}
    public MongoRefreshToken(String tokenHash, Long userId, Instant expiresAt, boolean revoked) {
        this.tokenHash = tokenHash;
        this.userId = userId;
        this.expiresAt = expiresAt;
        this.revoked = revoked;
    }
    public String getTokenHash() { return tokenHash; }
    public Long getUserId() { return userId; }
    public Instant getExpiresAt() { return expiresAt; }
    public boolean isRevoked() { return revoked; }
}
