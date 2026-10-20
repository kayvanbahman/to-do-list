package com.example.todo.auth.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringMongoRefreshTokenRepository extends MongoRepository<MongoRefreshToken, String> {
}
