package com.example.todo.todo.controller;

import com.example.todo.common.response.ApiResponse;
import com.example.todo.todo.document.Todo;
import com.example.todo.todo.dto.CreateTodoRequest;
import com.example.todo.todo.dto.UpdateTodoRequest;
import com.example.todo.todo.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService todos;

    public TodoController(TodoService todos) {
        this.todos = todos;
    }

    @GetMapping
    public ApiResponse<List<Todo>> list(Authentication authentication,
            @RequestParam(defaultValue = "false") boolean active) {
        return ApiResponse.success(todos.list(userId(authentication), active));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Todo> create(Authentication authentication, @Valid @RequestBody CreateTodoRequest request) {
        return ApiResponse.success(todos.create(userId(authentication), request));
    }

    @PatchMapping("/{id}")
    public ApiResponse<Todo> update(Authentication authentication, @PathVariable String id,
            @RequestBody UpdateTodoRequest request) {
        return ApiResponse.success(todos.update(userId(authentication), id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(Authentication authentication, @PathVariable String id) {
        todos.delete(userId(authentication), id);
        return ApiResponse.success(null);
    }

    private Long userId(Authentication authentication) {
        return ((com.example.todo.auth.security.AuthenticatedUser) authentication.getPrincipal()).id();
    }
}
