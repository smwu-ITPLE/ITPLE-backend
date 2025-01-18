package com.smwu_itple.backend.repository;

import com.smwu_itple.backend.domain.Pay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayRepository extends JpaRepository<Pay, Long> {
}
