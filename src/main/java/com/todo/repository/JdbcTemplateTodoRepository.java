package com.todo.repository;

import com.todo.dto.TodoResponseDto;
import com.todo.entity.Todo;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    @Override
    public TodoResponseDto saveTodo(Todo todo) {
        // SimpleJdbcInsert를 사용하면 데이터를 테이블에 INSERT할 수 있다
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        // 데이터를 INSERT할 테이블은 'todo'이고 만들어진 row의 id를 가져오기위해
        // withTableName("todo").usingGeneratedKeyColumns("id")
        jdbcInsert.withTableName("todo").usingGeneratedKeyColumns("id");
        // DB의 컬럼명과 일치한 key에 todo 객체의 값을 value로 넣어준다
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("text", todo.getText());
        parameters.put("password", todo.getPassword());
        parameters.put("created_at", todo.getCreatedAt());
        parameters.put("edited_at", todo.getEditedAt());
        parameters.put("user_id", todo.getUserId());

        String createdAtStr = todo.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String editedAtStr = todo.getEditedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        // SimpleJdbcInsert를 실행할 때 위에서 Map에 담은 데이터를 매핑하고 id 값을 받아온다
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        // ResponseDto를 생성해서 반환
        return new TodoResponseDto(
                key.longValue(),
                todo.getText(),
                createdAtStr,
                editedAtStr,
                todo.getUserId()
        );
    }

    @Transactional
    @Override
    public List<TodoResponseDto> findAllTodos(Long userId, String editedAt) {
        StringBuilder sql = new StringBuilder("SELECT * FROM todo");
        List<Object> params = new ArrayList<>();

        // userId가 1이상이거나 editedAt에 text 전달됐을 때
        if ((userId != null && userId > 0) || StringUtils.hasText(editedAt)) {
            sql.append(" WHERE");

            if (userId != null && userId > 0) {
                sql.append(" user_id = ?");
                params.add(userId);
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

        return jdbcTemplate.query(sql.toString(), params.toArray(new Object[0]), todoRowMapper());
    }

    @Transactional
    @Override
    public Todo findTodoByIdOrElseThrow(Long id) {
        List<Todo> result = jdbcTemplate.query("select * from todo where id = ?", todoRowMapperV2(), id);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 id = " + id));
    }

    @Transactional
    @Override
    public Todo updateTodoAndUserName(Long id, String text, String name) {
        int updatedTodo = jdbcTemplate.update(
                "UPDATE todo SET text = ?, edited_at = ? WHERE id = ?",
                text,
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                id
        );

        int updatedUser = jdbcTemplate.update(
                "UPDATE users SET name = ? WHERE id = (SELECT user_id FROM todo WHERE id = ?)",
                name,
                id
        );

        // 업데이트 되었는지 확인
        if (updatedTodo == 0 || updatedUser == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "업데이트할 데이터가 존재하지 않습니다.");
        }

        return findTodoByIdOrElseThrow(id);
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

    // TodoResponseDto 를 반환
    private RowMapper<TodoResponseDto> todoRowMapper() {
        return new RowMapper<TodoResponseDto>() {
            @Override
            public TodoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                String createdAt = rs.getTimestamp("created_at").toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                String editedAt = rs.getDate("edited_at").toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                return new TodoResponseDto(
                        rs.getLong("id"),
                        rs.getString("text"),
                        createdAt,
                        editedAt,
                        rs.getLong("user_id"));
            }
        };
    }

    // Todo 를 반환
    private RowMapper<Todo> todoRowMapperV2() {
        return new RowMapper<Todo>() {
            @Override
            public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Todo(
                        rs.getLong("id"),
                        rs.getString("text"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getDate("edited_at").toLocalDate(),
                        rs.getLong("user_id"));
            }
        };
    }
}
