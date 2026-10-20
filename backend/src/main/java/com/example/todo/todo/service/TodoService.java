package com.example.todo.todo.service;

import com.example.todo.common.exception.ResourceNotFoundException;
import com.example.todo.todo.document.Todo;
import com.example.todo.todo.dto.CreateTodoRequest;
import com.example.todo.todo.dto.UpdateTodoRequest;
import com.example.todo.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todos;

    public TodoService(TodoRepository todos) {
        this.todos = todos;
    }

    public List<Todo> list(Long userId, boolean activeOnly) {
        return activeOnly ? todos.findAllByUserIdAndDoneOrderByCreatedAtDesc(userId, false)
                : todos.findAllByUserIdOrderByCreatedAtDesc(userId);
    }

    public Todo create(Long userId, CreateTodoRequest request) {
        return todos.save(new Todo(userId, request.text().trim()));
    }

    public Todo update(Long userId, String id, UpdateTodoRequest request) {
        Todo todo = find(userId, id);
        if (request.text() != null && request.text().isBlank())
            throw new IllegalArgumentException("Todo text cannot be blank");
        todo.update(request.text(), request.done());
        return todos.save(todo);
    }

    public void delete(Long userId, String id) {
        todos.delete(find(userId, id));
    }

    private Todo find(Long userId, String id) {
        return todos.findByIdAndUserId(id, userId).orElseThrow(() -> new ResourceNotFoundException("Todo not found"));
    }
}
