package com.todo.repository;

import com.todo.dto.TodoResponseDto;
import com.todo.entity.Todo;

import java.util.List;

public interface TodoRepository {

    TodoResponseDto saveTodo(Todo todo);
    List<TodoResponseDto> findAllTodos(String name, String editedAt);
    Todo findTodoByIdOrElseThrow(Long id);
    int update(Long id, String text, String name);
    void deleteTodo(Long id, String password);
    List<TodoResponseDto> findTodoByUserId(Long id);
}
