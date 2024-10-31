package com.smwu_itple.backend.archieve;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArchieveService {

    @Autowired
    private ArchieveRepository archieveRepository;

    public Archieve saveArchieve(Archieve archieve) {
        return archieveRepository.save(archieve);
    }

    public Optional<Archieve> getArchieveById(Long id) {
        return archieveRepository.findById(id);
    }

    public List<Archieve> getAllArchieve() {
        return archieveRepository.findAll();
    }

    public void deleteArchieve(Long id) {
        archieveRepository.deleteById(id);
    }
}