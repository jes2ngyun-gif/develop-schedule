package com.sparta.developschedule.user.repository;

import com.sparta.developschedule.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {                     // UserSeivice가 DB에 직접 접근X, UserR 통해 저장해야 함.


}
