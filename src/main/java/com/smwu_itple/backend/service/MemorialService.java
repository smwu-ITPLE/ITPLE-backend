package com.smwu_itple.backend.service;

import com.smwu_itple.backend.domain.Memorial;
import com.smwu_itple.backend.repository.MemorialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemorialService {
    private final MemorialRepository memorialRepository;

    //조문공간 생성
    @Transactional
    public Long createMemorial(Memorial memorial){
        memorialRepository.save(memorial);
        return memorial.getId();
    }

    //조문공간 조회
    public List<Memorial> findMemorial(){
        return memorialRepository.findAll();
    }

    public Memorial findOne(Long id){
        return memorialRepository.findOne(id);
    }
}
