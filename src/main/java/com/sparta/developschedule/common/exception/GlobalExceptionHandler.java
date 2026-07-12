package com.sparta.developschedule.common.exception;

import com.sparta.developschedule.common.dto.MessageResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {                                                // NotFoundException이 발생하면 HTTP 404 Not Found상태코드와 메세지 응답을 내려줌.

    // 기존 : 404 처리 --> 조회 대상이 없을 때 404 Not Found 응답
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<MessageResponseDto> handleNotFound(NotFoundException e) {
        MessageResponseDto responseDto = new MessageResponseDto(e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
    }

    // @Valid 검증 실패 시 --> 400 Bad Request 응답
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageResponseDto> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        MessageResponseDto responseDto = new MessageResponseDto(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

    // 401 처리 --> 로그인 인증 실패 시 401 Unauthorized 응답
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<MessageResponseDto> handleUnauthorized(UnauthorizedException e) {
        MessageResponseDto responseDto = new MessageResponseDto(e.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDto);
    }

    // 권한이 없는 사용자가 수정/삭제하려고 할 때 --> 403 Forbidden 응답
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<MessageResponseDto> handleForbidden(ForbiddenException e) {
        MessageResponseDto responseDto = new MessageResponseDto(e.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseDto);
    }
}