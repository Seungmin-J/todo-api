package com.todo.dto;

import lombok.Getter;

@Getter
public class TodoRequestDto {

    private String text;
    private String password;
    private Long userId;
}
