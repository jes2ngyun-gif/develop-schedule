package com.sparta.developschedule.common.exception;

public class ForbiddenException extends RuntimeException {                  // 로그인 했지만 '그' 유저가 아님? -> 403 Forbidden

    public ForbiddenException(String message) {
        super(message);
    }
}
