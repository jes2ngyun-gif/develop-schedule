package com.sparta.developschedule.schedule.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateScheduleRequestDto {

    private String title;
    private String contents;
}
