package com.sparta.developschedule.schedule.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScheduleRequestDto {                   // 일정 생성 요청에서 사용할거임.

    private Long userId;
    private String title;
    private String contents;
}
