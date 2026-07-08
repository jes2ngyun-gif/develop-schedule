package com.sparta.developschedule.schedule.entity;

import com.sparta.developschedule.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    // 생성자
    public Schedule(String username, String title, String contents) {
        this.username = username;
        this.title = title;
        this.contents = contents;
    }

    // 수정 메 써 드
    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

}
