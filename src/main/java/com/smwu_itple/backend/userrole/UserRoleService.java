package com.smwu_itple.backend.userrole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRoleService {
    @Autowired
    private UserRoleRepository roleRepository;

    public UserRole saveRole(UserRole role) {
        return roleRepository.save(role);
    }

    public Optional<UserRole> getRoleById(Long id) {
        return roleRepository.findById(id);
    }
}
