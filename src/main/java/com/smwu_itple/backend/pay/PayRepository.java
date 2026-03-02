package com.smwu_itple.backend.pay;

import com.smwu_itple.backend.late.Late;
import com.smwu_itple.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayRepository extends JpaRepository<Pay, Long> {
    List<Pay> findByLate(Late late);
    List<Pay> findBySender(User sender);
}
