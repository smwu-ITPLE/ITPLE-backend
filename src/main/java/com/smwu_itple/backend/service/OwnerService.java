package com.smwu_itple.backend.service;

import com.smwu_itple.backend.domain.Owner;
import com.smwu_itple.backend.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepository;

    public Owner findOwnerByIdOrThrow(Long ownerId){
        return ownerRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 상주를 찾을 수 없습니다."));
    }
}
