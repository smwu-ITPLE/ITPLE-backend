package com.smwu_itple.backend.dto.request;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String phonenumber;
    private String passwd;
}
