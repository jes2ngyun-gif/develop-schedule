package com.sparta.developschedule.schedule.service;

import com.sparta.developschedule.schedule.dto.ScheduleRequestDto;
import com.sparta.developschedule.schedule.dto.ScheduleResponseDto;
import com.sparta.developschedule.schedule.dto.UpdateScheduleRequestDto;
import com.sparta.developschedule.schedule.entity.Schedule;
import com.sparta.developschedule.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    //  ScheduleService는 실제 일정 기능 처리하는 곳.

    private final ScheduleRepository scheduleRepository;                          // ScheduleService가 DB 작업을 하려면 ScheduleRepository가 필요

    // 일정 생성 기능
    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(
                requestDto.getUsername(),
                requestDto.getTitle(),
                requestDto.getContents()
        );

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(savedSchedule);
    }

    // 일정 전체 조회 기능
    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> getSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();                   // scheduleRepository.findAll()로 DB의 모든 일정 조회
        List<ScheduleResponseDto> responseDtoList = new ArrayList<>();             // 조회 결과는 List<Schedule> 형태

        for (Schedule schedule : schedules) {
            responseDtoList.add(new ScheduleResponseDto(schedule));
        }
        return responseDtoList;
    }

    // 일정 단건 조회 기능
    @Transactional(readOnly = true)
    public ScheduleResponseDto getSchedule(Long id) {
        Schedule schedule = findSchedule(id);                                        // findSchedule(id)-> id로 Schedule 찾기. 없으면 에러 발샏

        return new ScheduleResponseDto(schedule);
    }


    // 일정 수정 기능
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, UpdateScheduleRequestDto requestDto) {
        Schedule schedule = findSchedule(id);

        schedule.update(requestDto.getTitle(), requestDto.getContents());

        return new ScheduleResponseDto(schedule);
    }

    // 일정 삭제 기능
    @Transactional
    public void deleteSchedule(Long id) {                                                // void -> 삭제 후에 Service가 돌려줄 데이터 없기 때문.
        Schedule schedule = findSchedule(id);

        scheduleRepository.delete(schedule);
    }

    // id로 일정 찾는 공통 메서드
    private Schedule findSchedule(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다."));       // private -> 이 메서드를 스케줄서비스 내부에서만 사용하려고
    }
}

