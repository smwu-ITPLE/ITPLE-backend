package com.smwu_itple.backend.user.dto;

import lombok.Data;

@Data
public class UserSignupRequest {
    private String name;
    private String phonenumber;
    private String passwd;
}