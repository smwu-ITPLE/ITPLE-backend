package com.smwu_itple.backend.repository;

import com.smwu_itple.backend.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
