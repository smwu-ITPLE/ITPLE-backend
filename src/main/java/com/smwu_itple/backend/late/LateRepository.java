package com.smwu_itple.backend.late;

import com.smwu_itple.backend.user.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LateRepository {
    private final EntityManager em;

    public void save(Late late){
        em.persist(late);
    }

    public Late findOne(Long id){
        return em.find(Late.class, id);
    }

    public List<Late> findAll(){
        return em.createQuery("select l from Late l", Late.class)
                .getResultList();
    }

    public Optional<Late> findById(Long id) {
        return em.createQuery("select l from Late l where l.id = :id", Late.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst(); // 첫 번째 결과 반환
    }

    public Optional<Late> findByName(String name) {
        return em.createQuery("select l from Late l where l.name = :name", Late.class)
                .setParameter("name", name)
                .getResultStream()
                .findFirst(); // 첫 번째 결과 반환
    }

    // 특정 ID로 Late 삭제
    public void deleteById(Long id) {
        Late late = em.find(Late.class, id); // 해당 엔티티 조회
        if (late != null) {
            em.remove(late); // 엔티티가 존재하면 삭제
        }
    }

}
