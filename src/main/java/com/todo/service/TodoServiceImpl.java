package com.todo.service;

import com.todo.dto.TodoRequestDto;
import com.todo.dto.TodoResponseDto;
import com.todo.entity.Todo;
import com.todo.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        Todo todo = new Todo(dto.getText(), dto.getPassword(), dto.getUserId());
        return todoRepository.saveTodo(todo);
    }

    @Override
    public List<TodoResponseDto> findAllTodos(Long userId, String editedAt) {
        return todoRepository.findAllTodos(userId, editedAt);
    }

    @Override
    public TodoResponseDto findTodoById(Long id) {
        Todo todo = todoRepository.findTodoByIdOrElseThrow(id);
        return new TodoResponseDto(todo);
    }

    @Transactional
    public TodoResponseDto updateTodoAndUserName(Long id, String text, String name) {
        if (text == null || name == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "text와 name은 필수 입력값입니다.");
        }

        // 업데이트와 동시에 변경된 객체 반환
        Todo updatedTodo = todoRepository.updateTodoAndUserName(id, text, name);

        return new TodoResponseDto(updatedTodo);
    }

    @Override
    public void deleteTodo(Long id, String password) {
        todoRepository.deleteTodo(id, password);
    }
}
