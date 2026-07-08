package com.sparta.developschedule.common.dto;

import lombok.Getter;

@Getter
public class MessageResponseDto {

    private String message;

    public MessageResponseDto(String message) {
        this.message = message;
    }
}
