package com.example.todo.auth.repository.mysql;

import com.example.todo.auth.entity.User;
import com.example.todo.auth.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@Profile("mysql")
public class MySqlUserRepository implements UserRepository {
    private final JpaUserRepository jpa;

    public MySqlUserRepository(JpaUserRepository jpa) { this.jpa = jpa; }
    public Optional<User> findByEmailIgnoreCase(String email) { return jpa.findByEmailIgnoreCase(email); }
    public boolean existsByEmailIgnoreCase(String email) { return jpa.existsByEmailIgnoreCase(email); }
    public User save(User user) { return jpa.save(user); }
    public Optional<User> findById(Long id) { return jpa.findById(id); }
}
