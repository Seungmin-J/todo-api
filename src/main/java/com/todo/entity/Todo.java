package com.scheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {
    private Long id;
    private String todo;
    private String name;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;

    public Schedule(String todo, String name, String password) {
        this.todo = todo;
        this.name = name;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }
}
