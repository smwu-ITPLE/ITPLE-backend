package com.smwu_itple.backend.infra.api;

public enum SuccessStatus {
    _POST_USER_SIGNUP_SUCCESS("회원가입 성공"),
    _POST_USER_LOGIN_SUCCESS("로그인 성공"),
    _POST_USER_LOGOUT_SUCCESS("로그아웃 성공"),
    _GET_USER_PROFILE_SUCCESS("프로필 조회 성공"),
    _POST_LATE_CREATE_SUCCESS("조문공간 생성 성공"),
    _DELETE_LATE_SUCCESS("조문공간 삭제 성공"),
    _SEARCH_LATE_SUCCESS("조문공간 검색 성공"),
    _GET_LATE_SUCCESS("조문공간 조회 성공"),
    _GET_LATE_SHARE_SUCCESS("조문공간 공유 성공"),
    _GET_USER_LATELIST_SUCCESS("사용자가 상주로 포함된 조문공간 조회 성공"),
    _GET_LATEOWNER_SUCCESS("조문공간 상주 조회 성공");

    private final String message;

    SuccessStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}