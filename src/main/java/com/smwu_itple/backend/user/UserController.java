package com.smwu_itple.backend.user;

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

    //로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            User user = userService.login(loginDto.getPhonenumber(), loginDto.getPasswd());
            return ResponseEntity.ok(user); // 성공 시 유저 정보를 반환
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: " + e.getMessage());
        }
    }
}
