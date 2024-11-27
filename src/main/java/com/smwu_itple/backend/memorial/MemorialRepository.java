package com.smwu_itple.backend.memorial;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemorialRepository {
    private final EntityManager em;

    public void save(Memorial memorial){
        em.persist(memorial);
    }

    public Memorial findOne(Long id){
        return em.find(Memorial.class, id);
    }

    public List<Memorial> findAll(){
        return em.createQuery("select m from Memorial m", Memorial.class)
                .getResultList();
    }

    public Optional<Memorial> findById(Long id) {
        return em.createQuery("select m from Memorial m where m.id = :id", Memorial.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst(); // 첫 번째 결과 반환
    }

    public Optional<Memorial> findByName(String name) {
        return em.createQuery("select m from Memorial m where m.name = :name", Memorial.class)
                .setParameter("name", name)
                .getResultStream()
                .findFirst(); // 첫 번째 결과 반환
    }
}
