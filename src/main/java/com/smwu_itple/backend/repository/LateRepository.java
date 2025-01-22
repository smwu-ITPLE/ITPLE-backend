package com.smwu_itple.backend.repository;

import com.smwu_itple.backend.domain.Late;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LateRepository extends JpaRepository<Late, Long> {
    // 이름과 비밀번호로 조문공간 검색
    Optional<Late> findByNameAndPasswd(String name, String passwd);
}