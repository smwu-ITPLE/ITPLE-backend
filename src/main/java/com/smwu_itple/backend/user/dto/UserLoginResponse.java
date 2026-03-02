package com.smwu_itple.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserLoginResponse {
    private Long id;
    private String name;
    private String phonenumber;

}
