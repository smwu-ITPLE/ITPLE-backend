package com.smwu_itple.backend.infra.api;

import org.springframework.http.HttpStatus;

public enum FailureStatus {
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청"),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증 실패"),
    _NOT_FOUND(HttpStatus.NOT_FOUND, "데이터를 찾을 수 없음");


    private final HttpStatus httpStatus;
    private final String message;

    FailureStatus(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
