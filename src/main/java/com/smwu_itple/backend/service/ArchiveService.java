package com.smwu_itple.backend.service;

import com.smwu_itple.backend.domain.Archive;
import com.smwu_itple.backend.domain.Late;
import com.smwu_itple.backend.repository.ArchiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArchiveService {
    private final ArchiveRepository archieveRepository;
    //아카이브 생성
    @Transactional
    public Long saveArchieve(Archive archive){
        archieveRepository.save(archive);
        return archive.getId();
    }

    // 특정 Late에 속한 Archieve 리스트 조회
    public List<Archive> findArchivesByLate(Late late) {
        return archieveRepository.findAllByLate(late);
    }
}