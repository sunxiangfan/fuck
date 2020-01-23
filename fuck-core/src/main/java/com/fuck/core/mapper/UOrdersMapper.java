package com.fuck.core.mapper;

import com.fuck.core.entity.User;

import java.util.List;

public interface UOrdersMapper {

    int delete(Integer id);

    int insert(User user);

    int insertDynamic(User user);

    int updateDynamic(User user);

    User selectById(Integer id);

    List<User> selectDynamic(User user);
}