package com.smwu_itple.backend.repository;

import com.smwu_itple.backend.domain.Archive;
import com.smwu_itple.backend.domain.Late;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchiveRepository extends JpaRepository<Archive, Long> {
}