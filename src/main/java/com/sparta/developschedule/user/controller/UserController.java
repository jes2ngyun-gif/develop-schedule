package com.sparta.developschedule.user.controller;

import com.sparta.developschedule.common.dto.MessageResponseDto;
import com.sparta.developschedule.user.dto.UserSaveRequestDto;
import com.sparta.developschedule.user.dto.UserResponseDto;
import com.sparta.developschedule.user.dto.UserUpdateRequestDto;
import com.sparta.developschedule.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 유저 생성
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> createUser(
            @Valid @RequestBody UserSaveRequestDto requestDto                  // @Valid 로 요청 DTO 검증
    ) {
        UserResponseDto responseDto = userService.createUser(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 유저 전체 조회
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getUsers(

    ) {
        List<UserResponseDto> responseDtoList = userService.getUsers();

        return ResponseEntity.ok(responseDtoList);
    }

    // 유저 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(
            @PathVariable Long id
    ) {
        UserResponseDto responseDto = userService.getUser(id);

        return ResponseEntity.ok(responseDto);
    }

    // 유저 수정
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateRequestDto requestDto
    ) {
        UserResponseDto responseDto = userService.updateUser(id, requestDto);

        return ResponseEntity.ok(responseDto);
    }

    // 유저 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> deleteUser(
            @PathVariable Long id
    ) {
        userService.deleteUser(id);

        MessageResponseDto responseDto = new MessageResponseDto("유저가 삭제되었습니다.");

        return ResponseEntity.ok(responseDto);
    }
}

