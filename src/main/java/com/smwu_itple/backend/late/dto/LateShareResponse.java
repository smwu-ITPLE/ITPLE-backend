package com.smwu_itple.backend.late.dto;

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