package com.smwu_itple.backend.repository;

import com.smwu_itple.backend.domain.Archive;
import com.smwu_itple.backend.domain.Late;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArchiveRepository {
    private final EntityManager em;

    public void save(Archive archive) {em.persist(archive);}

    // 모든 아카이브 조회
    public List<Archive> findAll(){
        return em.createQuery("select a from Archive a", Archive.class)
                .getResultList();
    }

    // 특정 late에 속한 아카이브 조회
    public List<Archive> findAllByLate(Late late) {
        return em.createQuery("select a from Archive a where a.late = :late", Archive.class)
                .setParameter("memorial", late)
                .getResultList();
    }
}