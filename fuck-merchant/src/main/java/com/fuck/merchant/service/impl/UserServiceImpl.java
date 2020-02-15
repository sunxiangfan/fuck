package com.fuck.merchant.service.impl;

import com.fuck.merchant.entity.User;
import com.fuck.merchant.mapper.UserMapper;
import com.fuck.merchant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByMobileAndType(String mobile, String type) {
        return userMapper.findUserByMobileAndType(mobile, type);
    }

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int insertDynamic(User user) {
        return userMapper.insertDynamic(user);
    }

}