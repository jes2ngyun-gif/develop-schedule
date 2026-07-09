package com.sparta.developschedule.user.service;

import com.sparta.developschedule.user.dto.UserSaveRequestDto;
import com.sparta.developschedule.user.dto.UserResponseDto;
import com.sparta.developschedule.user.entity.User;
import com.sparta.developschedule.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
