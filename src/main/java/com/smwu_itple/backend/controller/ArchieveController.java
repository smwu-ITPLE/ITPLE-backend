package com.smwu_itple.backend.controller;

import com.smwu_itple.backend.domain.Archieve;
import com.smwu_itple.backend.service.ArchieveService;
import com.smwu_itple.backend.domain.Memorial;
import com.smwu_itple.backend.service.MemorialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memorials")
public class ArchieveController {

    private final ArchieveService archieveService;
    private final MemorialService memorialService;

    // 특정 Memorial에 속한 Archieve 리스트 조회
    @GetMapping("/{id}/archieve")
    public ResponseEntity<?> getArchievesByMemorial(@PathVariable("id") Long memorialId) {
        Memorial memorial = memorialService.findOne(memorialId);
        if (memorial == null) {
            return ResponseEntity.badRequest().body("Memorial ID가 유효하지 않습니다.");
        }

        List<Archieve> archieves = archieveService.findArchievesByMemorial(memorial);
        return ResponseEntity.ok(archieves);
    }

    // 새 글 작성
    @PostMapping("/{id}/archieve")
    public ResponseEntity<?> createArchieve(
            @PathVariable("id") Long memorialId,
            @RequestBody Archieve archieve) {

        Memorial memorial = memorialService.findOne(memorialId);
        if (memorial == null) {
            return ResponseEntity.badRequest().body("Memorial ID가 유효하지 않습니다.");
        }

        archieve.setMemorial(memorial);
        archieveService.saveArchieve(archieve);
        return ResponseEntity.ok("Archieve가 성공적으로 저장되었습니다.");
    }
}



