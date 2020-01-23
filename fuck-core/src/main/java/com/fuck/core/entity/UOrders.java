package com.fuck.core.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UOrders {

    private Long uId;

    private Long mId;

    private String uType;

    private Date createTime;

    private Date updateTime;
}