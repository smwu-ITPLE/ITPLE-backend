package com.smwu_itple.backend.controller;

import com.smwu_itple.backend.dto.ArchieveDTO;
import com.smwu_itple.backend.service.ArchieveService;
import com.smwu_itple.backend.domain.Memorial;
import com.smwu_itple.backend.dto.MemorialDto;
import com.smwu_itple.backend.service.MemorialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memorials")
public class MemorialController {
    private final MemorialService memorialService;
    private final ArchieveService archieveService;

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
            // Memorial 객체를 찾음
            Memorial memorial = memorialService.findOne(id);

            // Memorial이 없다면 404 오류 응답
            if (memorial == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("추모관을 찾을 수 없습니다.");
            }

            // Memorial에 포함된 Archieve들을 ArchieveDTO로 변환
            List<ArchieveDTO> archieveDTOs = memorial.getArchieves().stream()
                    .map(archieve -> new ArchieveDTO(archieve.getId(), archieve.getContent(), archieve.getColor()))
                    .collect(Collectors.toList());

            // MemorialDto 생성
            MemorialDto memorialDto = new MemorialDto();
            memorialDto.setTitle(memorial.getTitle());
            memorialDto.setContent(memorial.getContent());
            memorialDto.setMask(memorial.getMask());
            memorialDto.setImage(memorial.getImage());
            memorialDto.setMemorialUser(memorial.getMemorialUser());
            memorialDto.setArchieveDTOs(archieveDTOs);

            // MemorialDto 반환
            return ResponseEntity.ok(memorialDto);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }


}
