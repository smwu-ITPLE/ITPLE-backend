package com.smwu_itple.backend.repository;

import com.smwu_itple.backend.domain.Late;
import com.smwu_itple.backend.domain.Message;
import com.smwu_itple.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByLate(Late late);
    List<Message> findBySender(User sender);
}
