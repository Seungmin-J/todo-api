package com.scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private Long id;
    private String todo;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;
}
