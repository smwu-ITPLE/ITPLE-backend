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
    _GET_LATEOWNER_SUCCESS("조문공간 상주 조회 성공"),
    _POST_ARCHIVE_CREATE_SUCCESS("조문공간 아카이브 생성 성공"),
    _POST_MESSAGE_CREATE_SUCCESS("조문 메시지 전송 성공"),
    _POST_PAY_CREATE_SUCCESS("부의금 전송 성공"),
    _GET_MESSAGELIST_SUCCESS("조문 메시지 리스트 조회 성공"),
    _GET_PAYLIST_SUCCESS("부의금 리스트 조회 성공"),
    _PAYMENT_READY_SUCCESS("결제 요청 성공"),
    _PAYMENT_APPROVE_SUCCESS("결제 처리 성공");

    private final String message;

    SuccessStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}