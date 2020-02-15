package com.fuck.merchant.service;

import com.fuck.merchant.entity.User;

public interface UserService {

    User findUserByMobileAndType(String mobile, String type);

    int insert(User user);

    int insertDynamic(User user);
}
