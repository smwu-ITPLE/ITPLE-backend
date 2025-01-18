package com.smwu_itple.backend.repository;

import com.smwu_itple.backend.domain.Late;
import com.smwu_itple.backend.domain.User;
import com.smwu_itple.backend.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByUserAndLate(User user, Late late);
}
