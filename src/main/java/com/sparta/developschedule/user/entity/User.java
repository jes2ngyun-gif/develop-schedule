package com.sparta.developschedule.user.entity;

import com.sparta.developschedule.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    // 직접 만든 생성자 ( 회원가입할 때 사용할 생성자 )
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    // 수정 메서드
    public void update(String username, String email) {
        this.username = username;
        this.email = email;
    }


}
