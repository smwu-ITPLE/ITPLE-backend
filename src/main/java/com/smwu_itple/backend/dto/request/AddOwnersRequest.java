package com.smwu_itple.backend.dto.request;

import com.smwu_itple.backend.service.UserRoleService;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddOwnersRequest {
    private List<UserRoleService.OwnerRequest> ownerRequests;
}
