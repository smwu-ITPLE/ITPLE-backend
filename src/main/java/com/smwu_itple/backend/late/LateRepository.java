package com.smwu_itple.backend.late;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LateRepository extends JpaRepository<Late, Long> {
}
