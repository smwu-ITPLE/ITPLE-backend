package com.smwu_itple.backend.userrole;

import com.smwu_itple.backend.late.Late;
import com.smwu_itple.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByUserAndLate(User user, Late late);
}
