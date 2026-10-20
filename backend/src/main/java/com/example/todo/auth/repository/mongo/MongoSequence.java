package com.example.todo.auth.repository.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("sequences")
public class MongoSequence {
    @Id
    private String id;
    private long value;

    public String getId() { return id; }
    public long getValue() { return value; }
}
