package com.smwu_itple.backend.controller;

import com.smwu_itple.backend.domain.Archive;
import com.smwu_itple.backend.domain.Late;
import com.smwu_itple.backend.service.ArchiveService;
import com.smwu_itple.backend.service.LateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memorials")
public class ArchiveController {

    private final ArchiveService archiveService;
    private final LateService lateService;

    // 특정 Late에 속한 Archieve 리스트 조회
    @GetMapping("/{id}/archive")
    public ResponseEntity<?> getArchivesByMemorial(@PathVariable("id") Long lateId) {
        Late late = lateService.findOne(lateId);
        if (late == null) {
            return ResponseEntity.badRequest().body("late ID가 유효하지 않습니다.");
        }

        List<Archive> archives = archiveService.findArchivesByLate(late);
        return ResponseEntity.ok(archives);
    }

    // 새 글 작성
    @PostMapping("/{id}/archive")
    public ResponseEntity<?> createArchive(
            @PathVariable("id") Long lateId,
            @RequestBody Archive archive) {

        Late late = lateService.findOne(lateId);
        if (late == null) {
            return ResponseEntity.badRequest().body("late ID가 유효하지 않습니다.");
        }

        archive.setLate(late);
        archiveService.saveArchieve(archive);
        return ResponseEntity.ok("Archieve가 성공적으로 저장되었습니다.");
    }
}



