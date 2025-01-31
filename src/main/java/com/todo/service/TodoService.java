package com.todo.service;

import com.todo.dto.TodoRequestDto;
import com.todo.dto.TodoResponseDto;

import java.util.List;

public interface TodoService {
    TodoResponseDto saveTodo(TodoRequestDto dto);
    List<TodoResponseDto> findAllTodos(String name, String editedAt);
    TodoResponseDto findTodoById(Long id);
    TodoResponseDto update(Long id, String text, String name);
    void deleteTodo(Long id, String password);
    List<TodoResponseDto> findTodoByUserId(Long id);
}
