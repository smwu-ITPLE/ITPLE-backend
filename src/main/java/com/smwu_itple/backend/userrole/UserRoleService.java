package com.smwu_itple.backend.userrole;

import com.smwu_itple.backend.late.Late;
import com.smwu_itple.backend.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    @Transactional
    public List<UserRole> addOwners(Long lateId, List<OwnerRequest> ownerRequests) {
        List<UserRole> userRoles = new ArrayList<>();

        for (OwnerRequest request : ownerRequests) {
            // 새로운 User와 Late 객체 생성 (데이터베이스 조회 또는 임시 처리)
            User user = new User();
            user.setName(request.getName());
            user.setPhonenumber(request.getPhoneNumber());

            Late late = new Late();
            late.setId(lateId);

            // UserRole 생성 및 저장
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setLate(late);
            userRole.setRoleRelation(request.getRelation());
            userRole.setAuthority(UserRole.Authority.OWNER);

            userRoles.add(userRoleRepository.save(userRole));
        }

        return userRoles;
    }

    // 상주 요청 데이터 구조 정의
    @Getter
    @Setter
    public static class OwnerRequest {
        private String name;
        private String phoneNumber;
        private String relation;
    }
}
