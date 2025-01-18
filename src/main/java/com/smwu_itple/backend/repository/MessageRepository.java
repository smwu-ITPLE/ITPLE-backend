package com.smwu_itple.backend.repository;

import com.smwu_itple.backend.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
