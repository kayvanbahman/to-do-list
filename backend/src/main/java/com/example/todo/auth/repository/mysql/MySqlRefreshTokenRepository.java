package com.example.todo.auth.repository.mysql;

import com.example.todo.auth.entity.RefreshToken;
import com.example.todo.auth.entity.User;
import com.example.todo.auth.repository.RefreshTokenRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.Optional;

@Repository
@Profile("mysql")
public class MySqlRefreshTokenRepository implements RefreshTokenRepository {
    private final JpaRefreshTokenRepository jpa;

    public MySqlRefreshTokenRepository(JpaRefreshTokenRepository jpa) { this.jpa = jpa; }
    public RefreshToken save(RefreshToken token) { return jpa.save(token); }

    @Transactional
    public Optional<User> consume(String hash, Instant now) {
        return jpa.findByTokenHashAndRevokedFalse(hash).flatMap(token -> {
            if (!token.getExpiresAt().isAfter(now)) return Optional.empty();
            token.revoke();
            User user = token.getUser();
            user.getEmail(); // Initialize the lazy association before the transaction ends.
            return Optional.of(user);
        });
    }

    @Transactional
    public void revoke(String hash) {
        jpa.findByTokenHashAndRevokedFalse(hash).ifPresent(RefreshToken::revoke);
    }
}
