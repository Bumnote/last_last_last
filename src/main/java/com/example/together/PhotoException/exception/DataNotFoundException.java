package com.example.together.PhotoException.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * DataNotFoundException 은 RuntimeException 을 상속하여 만들었다.
 * 만약 DataNotFoundException 이 발생하면 @ResponseStatus 애너테이션에 의해 404 오류(HttpStatus.NOT_FOUND)가 나타날 것이다.
 * */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "요청하신 데이터를 찾을 수 없습니다.")
public class DataNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DataNotFoundException(String message) {
        super(message);
    }
}