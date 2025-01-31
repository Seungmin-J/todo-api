package com.todo.repository;

import ch.qos.logback.core.util.StringUtil;
import com.todo.dto.TodoResponseDto;
import com.todo.entity.Todo;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Repository
public class JdbcTemplateTodoRepository implements TodoRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateTodoRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
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
        parameters.put("edited_at", todo.getEditedAt());

        String createdAtStr = todo.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String editedAtStr = todo.getEditedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        return new TodoResponseDto(
                key.longValue(),
                todo.getText(),
                todo.getName(),
                createdAtStr,
                editedAtStr);
    }

    @Override
    public List<TodoResponseDto> findAllTodos(String name, String editedAt) {
        StringBuilder sql = new StringBuilder("SELECT * FROM todo");
        List<String> params = new ArrayList<>();

        if (StringUtils.hasText(name) || StringUtils.hasText(editedAt)) {
            sql.append(" WHERE");

            if (StringUtils.hasText(name)) {
                sql.append(" name = ?");
                params.add(name);
            }

            if (StringUtils.hasText(editedAt)) {
                if (!params.isEmpty()) {
                    sql.append(" AND");
                }
                sql.append(" edited_at = ?");
                params.add(editedAt);
            }
        }

        sql.append(" ORDER BY edited_at DESC");
        return jdbcTemplate.query(sql.toString(), params.toArray(), todoRowMapper());
    }

    @Override
    public Todo findTodoByIdOrElseThrow(Long id) {
        List<Todo> result = jdbcTemplate.query("select * from todo where id = ?", todoRowMapperV2(), id);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }



    @Override
    public int update(Long id, String text, String name) {
        return jdbcTemplate.update("update todo set text = ?, name = ?, edited_at = ? where id = ?", text, name, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),id);
    }

    @Override
    public void deleteTodo(Long id, String password) {
        String storedPassword = jdbcTemplate.queryForObject(
                "SELECT password FROM todo WHERE id = ?",
                new Object[]{id},
                String.class
        );

        if (password.equals(storedPassword)) {
            jdbcTemplate.update("DELETE FROM todo WHERE id = ?", id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }
    }

    @Override
    public List<TodoResponseDto> findTodoByUserId(Long id) {
        return jdbcTemplate.query("select * from todo where user",todoRowMapper());
    }

    private RowMapper<TodoResponseDto> todoRowMapper() {
        return new RowMapper<TodoResponseDto>() {
            @Override
            public TodoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                String createdAt = rs.getTimestamp("created_at").toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                String editedAt = rs.getDate("edited_at").toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                return new TodoResponseDto(
                        rs.getLong("id"),
                        rs.getString("text"),
                        rs.getString("name"),
                        createdAt,
                        editedAt);
            }
        };
    }

    private RowMapper<Todo> todoRowMapperV2() {
        return new RowMapper<Todo>() {
            @Override
            public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Todo(
                        rs.getLong("id"),
                        rs.getString("text"),
                        rs.getString("name"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getDate("edited_at").toLocalDate());
            }
        };
    }
}
