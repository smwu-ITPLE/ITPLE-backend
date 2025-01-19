package com.smwu_itple.backend.controller;

import com.smwu_itple.backend.domain.User;
import com.smwu_itple.backend.dto.request.UserLoginRequest;
import com.smwu_itple.backend.dto.request.UserSignupRequest;
import com.smwu_itple.backend.dto.response.UserProfileResponse;
import com.smwu_itple.backend.infra.api.ApiResponse;
import com.smwu_itple.backend.infra.api.FailureStatus;
import com.smwu_itple.backend.infra.api.SuccessStatus;
import com.smwu_itple.backend.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;


    //회원가입
    @PostMapping(value = "/signup")
    public ResponseEntity<ApiResponse> create(@RequestBody UserSignupRequest userSignupRequest) {
        try {
            // 회원가입 서비스 호출
            User user = new User();
            user.setName(userSignupRequest.getName());
            user.setPhonenumber(userSignupRequest.getPhonenumber());
            user.setPasswd(userSignupRequest.getPasswd());

            userService.join(user);
            return ApiResponse.onSuccess(null, SuccessStatus._POST_USER_SIGNUP_SUCCESS);
        } catch (IllegalStateException e) {
            return ApiResponse.onFailure(null, FailureStatus._BAD_REQUEST, "회원가입 실패: " + e.getMessage());
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody UserLoginRequest userLoginRequest, HttpSession session) {
        try {
            // 로그인 서비스 호출
            User user = userService.login(userLoginRequest.getPhonenumber(), userLoginRequest.getPasswd());
            session.setAttribute("userId", user.getId());

            // 사용자 정보 응답
            return ApiResponse.onSuccess(Map.of(
                    "id", user.getId(),
                    "name", user.getName(),
                    "phonenumber", user.getPhonenumber()
            ), SuccessStatus._POST_USER_LOGIN_SUCCESS);
        } catch (IllegalStateException e) {
            return ApiResponse.onFailure(null, FailureStatus._UNAUTHORIZED, "로그인 실패: " + e.getMessage());
        }
    }

    // 로그아웃
    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> logout(HttpSession session) {
        if (session.getAttribute("userId") == null) {
            return ApiResponse.onFailure(null, FailureStatus._BAD_REQUEST, "이미 로그아웃 상태입니다.");
        }

        // 세션 무효화
        session.invalidate();
        return ApiResponse.onSuccess(null, SuccessStatus._POST_USER_LOGOUT_SUCCESS);
    }



    // 세션을 이용한 프로필 조회
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> getProfile(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ApiResponse.onFailure(null, FailureStatus._UNAUTHORIZED, "로그인이 필요합니다.");
        }

        try {
            // 사용자 정보 조회
            User user = userService.findOne(userId);
            UserProfileResponse userProfileResponse = new UserProfileResponse(user.getName());
            return ApiResponse.onSuccess(userProfileResponse, SuccessStatus._GET_USER_PROFILE_SUCCESS);
        } catch (Exception e) {
            return ApiResponse.onFailure(null, FailureStatus._NOT_FOUND, "사용자 정보를 찾을 수 없습니다.");
        }
    }


}
