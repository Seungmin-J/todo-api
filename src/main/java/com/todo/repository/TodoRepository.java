package com.todo.repository;

import com.todo.dto.TodoResponseDto;
import com.todo.entity.Todo;

public interface ScheduleRepository {

    TodoResponseDto saveSchedule(Todo todo);
}
