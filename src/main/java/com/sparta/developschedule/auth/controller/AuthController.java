package com.sparta.developschedule.auth.controller;

import com.sparta.developschedule.auth.dto.LoginRequestDto;
import com.sparta.developschedule.auth.service.AuthService;
import com.sparta.developschedule.common.dto.MessageResponseDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<MessageResponseDto> login(
            @Valid @RequestBody LoginRequestDto requestDto,
            HttpSession session                          // 파라미터로 요청하면 Spring이 session 수첩을 자동으로 꺼내줌
    ) {
        Long userId = authService.login(requestDto);

        session.setAttribute("loginUserId", userId);  // 수첩에 "이 손님 = 1번 유저" 적기

        MessageResponseDto responseDto = new MessageResponseDto("로그인 되었습니다.");

        return ResponseEntity.ok(responseDto);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<MessageResponseDto> logout(HttpSession session) {
        session.invalidate();   // 세션 수첩을 통째로 폐기

        MessageResponseDto responseDto = new MessageResponseDto("로그아웃 되었습니다.");
        return ResponseEntity.ok(responseDto);
    }


}