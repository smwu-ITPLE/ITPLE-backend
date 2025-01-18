package com.smwu_itple.backend.service;

import com.smwu_itple.backend.domain.Late;
import com.smwu_itple.backend.repository.LateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LateService {
    private final LateRepository lateRepository;

    //조문공간 생성
    @Transactional
    public Long createLate(Late late){
        lateRepository.save(late);
        return late.getId();
    }

    //조문공간 입장
//    public Late enterLate(Long id, String passwd){
//        Optional<Late> optionalLate = lateRepository.findById(id);
//
//        // Late가 존재하지 않을 경우 예외 발생
//        Late late = optionalLate.orElseThrow(() ->
//                new IllegalStateException("존재하지 않는 아이디입니다."));
//
//        // 비밀번호가 일치하지 않을 경우 예외 발생
//        if (!late.getPasswd().equals(passwd)) {
//            throw new IllegalStateException("비밀번호가 올바르지 않습니다.");
//        }
//
//        return late; // 성공 시 Late 객체 반환
//
//    }

    //조문공간 조회
    public List<Late> findLates(){
        return lateRepository.findAll();
    }

    public Late findOne(Long id){
        return lateRepository.findOne(id);
    }

    // 특정 Late 삭제
    @Transactional
    public void deleteLate(Long lateId) {
        lateRepository.deleteById(lateId);
    }
}
