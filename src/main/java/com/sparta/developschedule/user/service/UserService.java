package com.sparta.developschedule.user.service;

import com.sparta.developschedule.common.exception.NotFoundException;
import com.sparta.developschedule.user.dto.UserSaveRequestDto;
import com.sparta.developschedule.user.dto.UserResponseDto;
import com.sparta.developschedule.user.entity.User;
import com.sparta.developschedule.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    // 유저 생성
    @Transactional
    public UserResponseDto createUser(UserSaveRequestDto requestDto) {
        User user = new User(
                requestDto.getUsername(),
                requestDto.getEmail()
        );
        User savedUser = userRepository.save(user);

        return new UserResponseDto(savedUser);
    }

    // 유저 전체 조회
    @Transactional(readOnly = true)
    public List<UserResponseDto> getUsers() {
        List<User> users = userRepository.findAll();               // E 목록에서 users 테이블의 모든 유저를 조회
        List<UserResponseDto> responseDtoList = new ArrayList<>(); // 응답용 D로 바꿔야해서 새 리스트 생성

        for (User user : users) {
            responseDtoList.add(new UserResponseDto(user));
        }
        return responseDtoList;
    }

    // 유저 단건 조회
    @Transactional(readOnly = true)
    public UserResponseDto getUser(Long id) {
        User user = findUser(id);

        return new UserResponseDto(user);
    }

    // id로 유저를 찾는 공통 메서트
    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다. id: " + id));
    }
}
