package com.fuck.merchant.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UOrders extends BaseEntity {

    private Long id;

    private Long userId;

    private Long mId;

    private String uType;

    private Date createTime;

    private Date updateTime;
}