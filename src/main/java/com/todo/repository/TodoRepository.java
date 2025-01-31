package com.todo.repository;

import com.todo.dto.TodoResponseDto;
import com.todo.entity.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {

    TodoResponseDto saveTodo(Todo todo);
    List<TodoResponseDto> findAllTodos(String name, String editedAt);

//    Optional<Todo> findTodoById(Long id);
    Todo findTodoByIdOrElseThrow(Long id);
    int update(Long id, String text, String name);
}
