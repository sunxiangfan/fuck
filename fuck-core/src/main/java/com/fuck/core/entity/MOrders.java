package com.fuck.core.entity;

import lombok.Data;

import java.util.Date;

@Data
public class MOrders {

    private Long id;

    private String mNo;

    private String mOrdersType;

    private Double mMoney;

    private String mUrl;

    private String mStatus;

    private Integer mExpirationTime;

    private String mP;

    private String mC;

    private String mA;

    private Date createTime;

    private Date updateTime;
}