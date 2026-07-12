package com.sparta.developschedule.user.service;

import com.sparta.developschedule.common.exception.ForbiddenException;
import com.sparta.developschedule.common.exception.NotFoundException;
import com.sparta.developschedule.user.dto.UserSaveRequestDto;
import com.sparta.developschedule.user.dto.UserResponseDto;
import com.sparta.developschedule.user.dto.UserUpdateRequestDto;
import com.sparta.developschedule.user.entity.User;
import com.sparta.developschedule.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    // 유저 생성
    @Transactional
    public UserResponseDto createUser(UserSaveRequestDto requestDto) {
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(
                requestDto.getUsername(),
                requestDto.getEmail(),
                encodedPassword
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

    // 유저 수정
    @Transactional
    public UserResponseDto updateUser(Long loginUserId, Long id, UserUpdateRequestDto requestDto) {
        checkOwner(loginUserId, id);

        User user = findUser(id);

        user.update(requestDto.getUsername(), requestDto.getEmail());               // 실제 수정은 Entity 의 update() 메서드로 처리함

        userRepository.flush();                                                      // .flush() -> 최신 modifiedAt 으로 반영됌

        return new UserResponseDto(user);
    }

    // 유저 삭제
    @Transactional
    public void deleteUser(Long loginUserId, Long id) {
        checkOwner(loginUserId, id);

        User user = findUser(id);

        userRepository.delete(user);                      // 없는 아이디면 findUser(id)에서 예외 발생
    }

    // 로그인한 유저와 요청 대상 유저가 같은지 확인
    private void checkOwner(Long loginUserId, Long targetUserId) {
        if (!loginUserId.equals(targetUserId)) {
            throw new ForbiddenException("본인만 수정/삭제할 수 있습니다.");
        }
    }



    // id로 유저를 찾는 공통 메서트
    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다. id: " + id));
    }
}

