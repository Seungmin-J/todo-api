package com.scheduler.controller;

import com.scheduler.entity.Schedule;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/schedules")
public class ScheduleController {


    @PostMapping
    public Schedule createSchedule() {
        return null;
    }
}
