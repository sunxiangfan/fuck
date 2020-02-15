package com.fuck.merchant.entity;

import lombok.Data;

@Data
public class BaseEntity {

    private String admin_token;
    private int pageNum = 1;
    private int pageSize = 10;
}
