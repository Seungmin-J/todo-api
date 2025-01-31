package com.todo.repository;

import com.todo.dto.TodoResponseDto;
import com.todo.entity.Todo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TodoRepositoryImpl implements TodoRepository {

    private final JdbcTemplate jdbcTemplate;

    public TodoRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TodoResponseDto saveTodo(Todo todo) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("todo").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("text", todo.getText());
        parameters.put("name", todo.getName());
        parameters.put("password", todo.getPassword());
        parameters.put("created_at", todo.getCreatedAt());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        return new TodoResponseDto(key.longValue(),
                todo.getText(),
                todo.getName(),
                todo.getCreatedAt(),
                todo.getEditedAt());
    }

    @Override
    public List<TodoResponseDto> findAllTodos() {
        return List.of();
    }
}
