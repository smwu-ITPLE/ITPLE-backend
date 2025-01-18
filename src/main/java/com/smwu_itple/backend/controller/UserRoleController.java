package com.smwu_itple.backend.controller;

import com.smwu_itple.backend.domain.UserRole;
import com.smwu_itple.backend.dto.request.AddOwnersRequest;
import com.smwu_itple.backend.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-role")
@RequiredArgsConstructor
public class UserRoleController {

    private final UserRoleService userRoleService;

    @PostMapping("/add-owners/{lateId}")
    public ResponseEntity<List<UserRole>> addOwners(
            @PathVariable Long lateId,  // URL 파라미터로 lateId 받기
            @RequestBody AddOwnersRequest addOwnersRequest  // AddOwnersRequest를 RequestBody로 받기
    ) {
        List<UserRole> userRoles = userRoleService.addOwners(lateId, addOwnersRequest.getOwnerRequests());
        return ResponseEntity.ok(userRoles);
    }
}