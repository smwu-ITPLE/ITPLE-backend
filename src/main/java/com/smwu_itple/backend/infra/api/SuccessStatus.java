package com.smwu_itple.backend.infra.api;

public enum SuccessStatus {
    _POST_USER_SIGNUP_SUCCESS("회원가입 성공"),
    _POST_USER_LOGIN_SUCCESS("로그인 성공"),
    _POST_USER_LOGOUT_SUCCESS("로그아웃 성공"),
    _GET_USER_PROFILE_SUCCESS("프로필 조회 성공");

    private final String message;

    SuccessStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}