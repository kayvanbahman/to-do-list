package com.example.todo.todo.repository.mongo;

import com.example.todo.todo.document.Todo;
import com.example.todo.todo.repository.TodoRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("mongo")
public class MongoTodoRepository implements TodoRepository {
    private final SpringMongoTodoRepository mongo;

    public MongoTodoRepository(SpringMongoTodoRepository mongo) { this.mongo = mongo; }
    public List<Todo> findAllByUserIdOrderByCreatedAtDesc(Long id) {
        return mongo.findAllByUserIdOrderByCreatedAtDesc(id);
    }
    public List<Todo> findAllByUserIdAndDoneOrderByCreatedAtDesc(Long id, boolean done) {
        return mongo.findAllByUserIdAndDoneOrderByCreatedAtDesc(id, done);
    }
    public Optional<Todo> findByIdAndUserId(String id, Long userId) {
        return mongo.findByIdAndUserId(id, userId);
    }
    public Todo save(Todo todo) { return mongo.save(todo); }
    public void delete(Todo todo) { mongo.delete(todo); }
}
