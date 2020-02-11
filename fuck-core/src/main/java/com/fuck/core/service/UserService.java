package com.fuck.core.service;

import com.fuck.core.entity.User;

public interface UserService {

    User findUserByMobileAndType(String mobile, String type);

    int insert(User user);

    int insertDynamic(User user);
}
