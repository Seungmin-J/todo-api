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
    private String name;
    private String password;
    private LocalDateTime createdAt;
    private LocalDate editedAt;

    public Todo(String text, String name, String password) {
        this.text = text;
        this.name = name;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.editedAt = LocalDate.now();
    }

    public Todo(Long id, String text, String name, LocalDateTime createdAt, LocalDate editedAt) {
        this.id = id;
        this.text = text;
        this.name = name;
        this.createdAt = createdAt;
        this.editedAt = editedAt;
    }
}
