package com.smwu_itple.backend.repository;

import com.smwu_itple.backend.domain.Late;
import com.smwu_itple.backend.domain.Message;
import com.smwu_itple.backend.domain.Pay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayRepository extends JpaRepository<Pay, Long> {
    List<Pay> findByLate(Late late);
}
