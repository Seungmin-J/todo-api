package com.todo.service;

import com.todo.dto.TodoRequestDto;
import com.todo.dto.TodoResponseDto;
import com.todo.entity.Todo;
import com.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService{
    private final TodoRepository scheduleRepository;

    public ScheduleServiceImpl(TodoRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public TodoResponseDto saveSchedule(TodoRequestDto dto) {
        Todo schedule = new Todo(dto.getText(), dto.getName(), dto.getPassword());
        return scheduleRepository.saveSchedule(schedule);
    }
}
