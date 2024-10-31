package com.smwu_itple.backend.memorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemorialService {

    @Autowired
    private MemorialRepository memorialRepository;

    public Memorial saveMemorial(Memorial memorial) {
        return memorialRepository.save(memorial);
    }

    public Optional<Memorial> getMemorialById(Long id) {
        return memorialRepository.findById(id);
    }

    public List<Memorial> getAllMemorial() {
        return memorialRepository.findAll();
    }

}
