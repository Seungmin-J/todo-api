package com.todo.service;

import com.todo.dto.TodoRequestDto;
import com.todo.dto.TodoResponseDto;

public interface ScheduleService {
    TodoResponseDto saveSchedule(TodoRequestDto dto);
}
