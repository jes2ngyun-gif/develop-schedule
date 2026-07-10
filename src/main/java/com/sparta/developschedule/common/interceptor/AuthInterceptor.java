package com.sparta.developschedule.common.interceptor;

import com.sparta.developschedule.common.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor  {

        @Override                     // Controller 실행 전에 먼저 로그인 여부를 검사하는 메서드
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

            HttpSession session = request.getSession(false);   // 세션 없으면 null (새로 안 만듦)

            if (session == null || session.getAttribute("loginUserId") == null) {
                                                       // session.getAttribute("loginUserId") -> login 때 세션에 적어둔 그 이름표
                                                       // 이게 있으면 로그인한 사람, null이면 안 한 사람.
                throw new UnauthorizedException("로그인이 필요합니다.");
            }

            return true;   // 세션 있음 -> 통과! -> 요청이 컨트롤러로 넘어감!!
        }
    }



