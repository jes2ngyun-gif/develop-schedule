package com.sparta.developschedule.user.controller;

import com.sparta.developschedule.user.dto.UserSaveRequestDto;
import com.sparta.developschedule.user.dto.UserResponseDto;
import com.sparta.developschedule.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 유저 생성
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> createUser(
            @RequestBody UserSaveRequestDto requestDto
    ) {
        UserResponseDto responseDto = userService.createUser(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
