package com.sparta.developschedule.schedule.entity;

import com.sparta.developschedule.common.BaseEntity;
import com.sparta.developschedule.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 여러 일정은 한명의 유저에게 속함. 1:n = user:schedules
    // \so Schedule 입장에서 여러 Schedule → 하나의 User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;                                                  // 일정 테이블에 user_id를 저장하고, 그 user_id가 users 테이블의 id를 참조

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    // 생성자
    public Schedule(User user, String title, String contents) {
        this.user = user;
        this.title = title;
        this.contents = contents;
    }

    // 수정 메 써 드
    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

}
