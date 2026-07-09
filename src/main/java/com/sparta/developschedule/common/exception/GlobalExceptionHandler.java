package com.sparta.developschedule.common.exception;

import com.sparta.developschedule.common.dto.MessageResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {                                                // NotFoundException이 발생하면 HTTP 404 Not Found상태코드와 메세지 응답을 내려줌.

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<MessageResponseDto> handleNotFound(NotFoundException e) {
        MessageResponseDto responseDto = new MessageResponseDto(e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
    }
}