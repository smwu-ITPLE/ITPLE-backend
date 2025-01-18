package com.smwu_itple.backend.repository;

import com.smwu_itple.backend.domain.Archieve;
import com.smwu_itple.backend.domain.Memorial;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArchieveRepository {
    private final EntityManager em;

    public void save(Archieve archieve) {em.persist(archieve);}

    // 모든 아카이브 조회
    public List<Archieve> findAll(){
        return em.createQuery("select a from Archieve a", Archieve.class)
                .getResultList();
    }

    // 특정 Memorial에 속한 아카이브 조회
    public List<Archieve> findAllByMemorial(Memorial memorial) {
        return em.createQuery("select a from Archieve a where a.memorial = :memorial", Archieve.class)
                .setParameter("memorial", memorial)
                .getResultList();
    }
}