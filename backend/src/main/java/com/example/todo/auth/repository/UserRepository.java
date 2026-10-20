package com.example.todo.auth.repository;

import com.example.todo.auth.entity.User;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
    User save(User user);
    Optional<User> findById(Long id);
}
