package com.smwu_itple.backend.userrole;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddOwnersRequest {
    private List<UserRoleService.OwnerRequest> ownerRequests;
}
