package com.fuck.merchant.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private Long id;

    private String name;

    private String mobile;

    private String password;

    private Double balance;

    private Double freeze;

    private String merchantno;

    private String type;

    private String invitationCode;

    private String superiorCode;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;
}