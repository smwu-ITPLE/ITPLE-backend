package com.smwu_itple.backend.late;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/lates")
public class LateController {
    private final LateService lateService;

    // 1. 조문공간 정보 입력 후 확인 (조문공간 생성 준비)
    @PostMapping("/check")
    public ResponseEntity<String> checkLateInfo(@RequestBody LateDto lateDto) {
        try {
            // 입력 정보 확인 로직
            if (lateDto.getName() == null || lateDto.getDatePass() == null) {
                throw new IllegalArgumentException("필수 정보가 누락되었습니다.");
            }
            return ResponseEntity.ok("조문공간 정보 확인 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("조문공간 정보 확인 실패: " + e.getMessage());
        }
    }

    // 2. 상주 정보 입력 후 조문공간 생성
    @PostMapping("/create")
    public ResponseEntity<Long> createLate(@RequestBody LateDto lateDto) {
        try {
            // Late 객체 생성 및 설정
            Late late = new Late();
            late.setName(lateDto.getName());
            late.setProfile(late.getProfile());
            late.setAge(lateDto.getAge());
            late.setGender(lateDto.isGender());
            late.setDatePass(lateDto.getDatePass());
            late.setDateDeath(lateDto.getDateDeath());
            late.setLocation(lateDto.getLocation());
            late.setContent(lateDto.getContent());

            // 조문공간 생성
            Long lateId = lateService.createLate(late);
            return ResponseEntity.ok(lateId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //특정 조문공간 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getLate(@PathVariable Long id) {
        try {
            Late late = lateService.findOne(id);
            return ResponseEntity.ok(late);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("조문공간을 찾을 수 없습니다.");
        }
    }

    // 특정 조문공간 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLate(@PathVariable Long id) {
        try {
            lateService.deleteLate(id);
            return ResponseEntity.ok("조문공간 삭제 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("조문공간 삭제 실패: " + e.getMessage());
        }
    }


}