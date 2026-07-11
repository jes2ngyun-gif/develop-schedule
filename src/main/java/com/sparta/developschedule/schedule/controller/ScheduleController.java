package com.sparta.developschedule.schedule.controller;

import com.sparta.developschedule.common.dto.MessageResponseDto;
import com.sparta.developschedule.schedule.dto.ScheduleRequestDto;
import com.sparta.developschedule.schedule.dto.ScheduleResponseDto;
import com.sparta.developschedule.schedule.dto.UpdateScheduleRequestDto;
import com.sparta.developschedule.schedule.service.ScheduleService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;                           // C는 실제 기능을 직접 처리하지 않고 S한테 맡김.

    // 일정 생성
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @Valid @RequestBody ScheduleRequestDto requestDto,
        HttpSession session
    ) {
        Long loginUserId = (Long) session.getAttribute("loginUserId");         // 로그인할 때 세션에 저장한 값

        ScheduleResponseDto responseDto = scheduleService.createSchedule(loginUserId, requestDto);    // 일정 작성자는 ("loginUserId", userId) 로 결정됌.

        return ResponseEntity.status(HttpStatus. CREATED).body(responseDto);
    }


    // 일정 전체 조회
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getSchedules() {
        List<ScheduleResponseDto> responseDtoList = scheduleService.getSchedules();

        return ResponseEntity.ok(responseDtoList);
    }

    // 일정 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(
            @PathVariable Long id
    ) {
        ScheduleResponseDto responseDto = scheduleService.getSchedule(id);

        return ResponseEntity.ok(responseDto);
    }

    // 일정 수정
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody UpdateScheduleRequestDto requestDto
    ) {
        ScheduleResponseDto responseDto = scheduleService.updateSchedule(id, requestDto);

        return ResponseEntity.ok(responseDto);
    }

    // 일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);

        MessageResponseDto responseDto = new MessageResponseDto("일정이 삭제되었습니다.");

        return ResponseEntity.ok(responseDto);
    }
}
