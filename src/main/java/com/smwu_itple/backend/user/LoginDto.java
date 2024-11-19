package com.smwu_itple.backend.user;

import lombok.Data;

@Data
public class LoginDto {
    private String phonenumber;
    private String passwd;
}
