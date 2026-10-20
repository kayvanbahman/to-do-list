package com.example.todo.todo.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateTodoRequest(@NotBlank String text) {
}
