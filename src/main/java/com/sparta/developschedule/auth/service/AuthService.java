package com.sparta.developschedule.auth.service;

import com.sparta.developschedule.auth.dto.LoginRequestDto;
import com.sparta.developschedule.common.exception.UnauthorizedException;
import com.sparta.developschedule.user.entity.User;
import com.sparta.developschedule.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    // 로그인 : 성공하면 userId를 돌려줌
    @Transactional(readOnly = true)
    public Long login(LoginRequestDto requestDto) {

        //  이메일로 유저 찾기 (없으면 예외)
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new UnauthorizedException("이메일 또는 비밀번호가 일치하지 않습니다."));

        //  비밀번호 비교 (다르면 예외)
        if (!user.getPassword().equals(requestDto.getPassword())) {
            throw new UnauthorizedException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        //  검증 통과 -> userId 반환
        return user.getId();
    }
}