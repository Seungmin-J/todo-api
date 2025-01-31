package com.todo.controller;

import com.todo.dto.TodoRequestDto;
import com.todo.dto.TodoResponseDto;
import com.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto dto) {
        return new ResponseEntity<>(todoService.saveTodo(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<TodoResponseDto> findAllTodos(@RequestParam(required = false) String name,
                                              @RequestParam(required = false) String editedAt) {
        return todoService.findAllTodos(name, editedAt);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> findTodoById(@PathVariable Long id) {
        return new ResponseEntity<>(todoService.findTodoById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponseDto> update(@PathVariable Long id, @RequestParam String text, @RequestParam String name) {
        return new ResponseEntity<>(todoService.update(id, text, name), HttpStatus.OK);
    }


}
