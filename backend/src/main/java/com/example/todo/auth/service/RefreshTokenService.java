package com.example.todo.auth.service;

import com.example.todo.auth.entity.RefreshToken;
import com.example.todo.auth.entity.User;
import com.example.todo.auth.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository repository;
    private final long expirationSeconds;
    private final SecureRandom random = new SecureRandom();

    public RefreshTokenService(RefreshTokenRepository repository,
            @Value("${app.jwt.refresh-expiration-seconds}") long expirationSeconds) {
        this.repository = repository;
        this.expirationSeconds = expirationSeconds;
    }

    public String create(User user) {
        byte[] bytes = new byte[48];
        random.nextBytes(bytes);
        String raw = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        repository.save(new RefreshToken(user, hash(raw), Instant.now().plusSeconds(expirationSeconds)));
        return raw;
    }

    public User validateAndRotate(String rawToken) {
        return repository.consume(hash(rawToken), Instant.now())
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired refresh token"));
    }

    public void revoke(String rawToken) {
        repository.revoke(hash(rawToken));
    }

    private String hash(String value) {
        try {
            return HexFormat.of()
                    .formatHex(MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception ex) {
            throw new IllegalStateException("Could not hash token", ex);
        }
    }
}
