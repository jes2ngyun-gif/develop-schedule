package com.sparta.developschedule.user.dto;

import com.sparta.developschedule.user.entity.User;
import lombok.Getter;


import java.time.LocalDateTime;

@Getter
public class UserResponseDto {

    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
        this.modifiedAt = user.getModifiedAt();
    }

}
