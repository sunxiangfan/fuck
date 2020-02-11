package com.fuck.core.mapper;

import com.fuck.core.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    int delete(Long id);

    int insert(User user);

    int insertDynamic(User user);

    int updateDynamic(User user);

    User selectById(Integer id);

    List<User> selectDynamic(User user);

    int freezeMoney(double money);

    User findUserByMobileAndType(@Param("mobile") String mobile, @Param("type") String type);
}