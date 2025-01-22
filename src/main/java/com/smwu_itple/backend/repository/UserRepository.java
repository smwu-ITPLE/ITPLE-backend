package com.smwu_itple.backend.repository;

import com.smwu_itple.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhonenumber(String phonenumber);
    Optional<User> findByPhonenumberAndPasswd(String phonenumber, String passwd);

}