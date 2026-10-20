package com.example.todo.auth.repository;

import com.example.todo.auth.entity.RefreshToken;
import com.example.todo.auth.entity.User;
import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken token);
    Optional<User> consume(String tokenHash, Instant now);
    void revoke(String tokenHash);
}
