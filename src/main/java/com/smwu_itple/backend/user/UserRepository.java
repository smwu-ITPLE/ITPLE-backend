package com.smwu_itple.backend.user;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    public void save(User user){
        em.persist(user);
    }

    public User findOne(Long id){
        return em.find(User.class, id);
    }

    public List<User> findAll(){
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    public Optional<User> findByPhone(String phonenumber) {
         return em.createQuery("select u from User u where u.phonenumber = :phonenumber", User.class)
                 .setParameter("phonenumber", phonenumber)
                 .getResultStream()
                 .findFirst(); // 첫 번째 결과 반환
    }

}
