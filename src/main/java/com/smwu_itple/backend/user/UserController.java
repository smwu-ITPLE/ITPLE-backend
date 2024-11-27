package com.smwu_itple.backend.user;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;


    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> create(@RequestBody UserDto userDto) {
        try {
            User user = new User();
            user.setName(userDto.getName());
            user.setPhonenumber(userDto.getPhonenumber());
            user.setPasswd(userDto.getPasswd());

            userService.join(user);
            return ResponseEntity.ok("회원가입 성공");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패: " + e.getMessage());
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto, HttpSession session) {
        try {
            // 사용자 인증
            User user = userService.login(loginDto.getPhonenumber(), loginDto.getPasswd());
            session.setAttribute("userId", user.getId()); // 세션에 사용자 ID 저장
            return ResponseEntity.ok("로그인 성공");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: " + e.getMessage());
        }
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.OK).body("이미 로그아웃 상태입니다.");
        }

        session.invalidate(); // 세션 무효화
        return ResponseEntity.ok("로그아웃 성공");
    }


    // 세션을 이용한 프로필 조회
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        User user = userService.findOne(userId);
        return ResponseEntity.ok(user);
    }
}
