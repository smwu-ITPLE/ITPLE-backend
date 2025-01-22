package com.smwu_itple.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LateShareResponse {
    private String name;
    private String passwd;
    private String content;
    private String userName;
    private String userPhonenumber;
}