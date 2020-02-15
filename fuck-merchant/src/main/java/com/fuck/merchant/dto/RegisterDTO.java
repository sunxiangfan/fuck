package com.fuck.merchant.dto;

import lombok.Data;

@Data
public class RegisterDTO {

    private String mobile;

    private String code;

    private String password;

    private String confirmPassword;

    private String type;

    private String superiorCode;
}