package com.sparta.developschedule.schedule.dto;

import com.sparta.developschedule.schedule.entity.Schedule;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {

    private Long id;
    private Long userId;
    private String username;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.userId = schedule.getUser().getId();
        this.username = schedule.getUser().getUsername();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.createdAt = schedule.getCreatedAt();
        this.modifiedAt = schedule.getModifiedAt();
    }
}
