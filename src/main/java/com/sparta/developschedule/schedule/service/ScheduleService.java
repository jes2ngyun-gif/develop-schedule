package com.sparta.developschedule.schedule.service;

import com.sparta.developschedule.common.exception.ForbiddenException;
import com.sparta.developschedule.schedule.dto.ScheduleRequestDto;
import com.sparta.developschedule.schedule.dto.ScheduleResponseDto;
import com.sparta.developschedule.schedule.dto.UpdateScheduleRequestDto;
import com.sparta.developschedule.schedule.entity.Schedule;
import com.sparta.developschedule.schedule.repository.ScheduleRepository;
import com.sparta.developschedule.user.entity.User;
import com.sparta.developschedule.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sparta.developschedule.common.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    //  ScheduleService는 실제 일정 기능 처리하는 곳.

    private final ScheduleRepository scheduleRepository;                          // ScheduleService가 DB 작업을 하려면 ScheduleRepository가 필요

    private final UserRepository userRepository;

    // 일정 생성 기능
    @Transactional
    public ScheduleResponseDto createSchedule(Long loginUserId, ScheduleRequestDto requestDto) {

        User user = findUser(loginUserId);                                        // 작성자 = 세션에 저장된 로그인 유저 id
        // 이제 클라이언트가 작성자를 조작할 수 없음.
        Schedule schedule = new Schedule(
                user,
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
    public ScheduleResponseDto updateSchedule(Long id, Long loginUserId, UpdateScheduleRequestDto requestDto) {
        Schedule schedule = findSchedule(id);

        checkOwner(schedule, loginUserId);                        // 작성자 본인인지 확인!

        schedule.update(requestDto.getTitle(), requestDto.getContents());

        scheduleRepository.flush();

        return new ScheduleResponseDto(schedule);
    }

    // 일정 삭제 기능
    @Transactional
    public void deleteSchedule(Long id, Long loginUserId) {                                                // void -> 삭제 후에 Service가 돌려줄 데이터 없기 때문.
        Schedule schedule = findSchedule(id);

        checkOwner(schedule, loginUserId);

        scheduleRepository.delete(schedule);
    }

    // id로 일정 찾는 공통 메서드
    private Schedule findSchedule(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("일정을 찾을 수 없습니다. id: " + id));       // private -> 이 메서드를 스케줄서비스 내부에서만 사용하려고
    }

    // id로 유저 찾는 공통 메서드
    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다. id: " + id));
    }

    // 로그인한 유저가 일정의 작성자인지 확인
    private void checkOwner(Schedule schedule, Long loginUserId) {
        if (!schedule.getUser().getId().equals(loginUserId)) {
            throw new ForbiddenException("본인이 작성한 일정만 수정/삭제할 수 있습니다.");
        }
    }
}

