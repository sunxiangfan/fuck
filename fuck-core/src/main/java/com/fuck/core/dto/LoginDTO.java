package com.fuck.core.dto;

import lombok.Data;

@Data
public class LoginDTO {

    private String mobile;

    private String password;

    private String type;
}