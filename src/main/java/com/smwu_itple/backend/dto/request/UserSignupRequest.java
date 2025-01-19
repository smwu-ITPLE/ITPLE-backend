package com.smwu_itple.backend.dto.request;

import lombok.Data;

@Data
public class UserSignupRequest {
    private String name;
    private String phonenumber;
    private String passwd;
}