package com.smwu_itple.backend.late;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LateService {
    @Autowired
    private LateRepository lateRepository;

    public Late createLate(Late late){
        return lateRepository.save(late);
    }

    public Optional<Late> getLateById(Long lateId){
        return lateRepository.findById(lateId);
    }

    public void deleteLate(Long lateId){
        lateRepository.deleteById(lateId);
    }
}
