package com.todo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Todo {
    private Long id;
    private String text;
    private String password;
    private LocalDateTime createdAt;
    private LocalDate editedAt;
    private Long userId;

    public Todo(String text, String password, Long userId) {
        this.text = text;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.editedAt = LocalDate.now();
        this.userId = userId;
    }

    public Todo(Long id, String text, LocalDateTime createdAt, LocalDate editedAt, Long userId) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.editedAt = editedAt;
        this.userId = userId;
    }
}
