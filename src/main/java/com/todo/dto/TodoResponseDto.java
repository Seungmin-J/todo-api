package com.todo.dto;

import com.todo.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class TodoResponseDto {

    private Long id;
    private String text;
    private String createdAt;
    private String editedAt;
    private Long userId;

    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.text = todo.getText();
        this.createdAt = todo.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.editedAt = todo.getEditedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.userId = todo.getUserId();
    }
}
