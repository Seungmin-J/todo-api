package com.todo.service;

import com.todo.dto.TodoRequestDto;
import com.todo.dto.TodoResponseDto;
import com.todo.entity.Todo;
import com.todo.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public TodoResponseDto saveTodo(TodoRequestDto dto) {
        Todo todo = new Todo(dto.getText(), dto.getName(), dto.getPassword());
        return todoRepository.saveTodo(todo);
    }

    @Override
    public List<TodoResponseDto> findAllTodos(String name, String editedAt) {
        return todoRepository.findAllTodos(name, editedAt);
    }

    @Override
    public TodoResponseDto findTodoById(Long id) {
        Todo todo = todoRepository.findTodoByIdOrElseThrow(id);
        return new TodoResponseDto(todo);
    }

    @Override
    public TodoResponseDto update(Long id, String text, String name) {
        if (text == null || name == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int updated = todoRepository.update(id, text, name);
        if (updated == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Todo todo = todoRepository.findTodoByIdOrElseThrow(id);
        return new TodoResponseDto(todo);
    }
}
