package com.smwu_itple.backend.message;

import com.smwu_itple.backend.late.Late;
import com.smwu_itple.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByLate(Late late);
    List<Message> findBySender(User sender);
}
