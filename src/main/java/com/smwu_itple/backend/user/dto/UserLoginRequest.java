package com.smwu_itple.backend.user.dto;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String phonenumber;
    private String passwd;
}
