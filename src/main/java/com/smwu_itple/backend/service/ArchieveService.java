package com.smwu_itple.backend.service;

import com.smwu_itple.backend.repository.ArchieveRepository;
import com.smwu_itple.backend.domain.Archieve;
import com.smwu_itple.backend.domain.Memorial;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArchieveService {
    private final ArchieveRepository archieveRepository;
    //아카이브 생성
    @Transactional
    public Long saveArchieve(Archieve archieve){
        archieveRepository.save(archieve);
        return archieve.getId();
    }

    // 특정 Memorial에 속한 Archieve 리스트 조회
    public List<Archieve> findArchievesByMemorial(Memorial memorial) {
        return archieveRepository.findAllByMemorial(memorial);
    }
}