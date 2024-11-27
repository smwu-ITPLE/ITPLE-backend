package com.smwu_itple.backend.memorial;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memorials")
public class MemorialController {
    private final MemorialService memorialService;

    // 1. 공개 추모관 생성
    @PostMapping("/create")
    public ResponseEntity<Long> createMemorial(@RequestBody MemorialDto memorialDto) {
        try {

            Memorial memorial = new Memorial();
            memorial.setTitle(memorialDto.getTitle());
            memorial.setContent(memorialDto.getContent());
            memorial.setMask(memorialDto.getMask());
            memorial.setImage(memorialDto.getImage());
            // 추모관의 사용자 정보도 설정
            // 예시로 간단히 설정, 실제로는 사용자 인증을 통해 설정해야 함
            memorial.setMemorialUser(memorialDto.getMemorialUser());

            Long memorialId = memorialService.createMemorial(memorial);
            return ResponseEntity.ok(memorialId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // 2. 모든 추모관 조회
    @GetMapping("/")
    public ResponseEntity<List<Memorial>> getAllMemorials() {
        try {
            List<Memorial> memorials = memorialService.findMemorial();
            return ResponseEntity.ok(memorials);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 3. 특정 추모관 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getMemorial(@PathVariable Long id) {
        try {
            Memorial memorial = memorialService.findOne(id);
            return ResponseEntity.ok(memorial);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("추모관을 찾을 수 없습니다.");
        }
    }
}
