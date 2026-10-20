package com.example.todo.auth.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface SpringMongoUserRepository extends MongoRepository<MongoUser, Long> {
    Optional<MongoUser> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
}
