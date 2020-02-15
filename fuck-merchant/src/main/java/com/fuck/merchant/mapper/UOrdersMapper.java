package com.fuck.merchant.mapper;

import com.fuck.merchant.entity.UOrders;
import com.fuck.merchant.entity.User;

import java.util.List;

public interface UOrdersMapper {

    int delete(Integer id);

    int insert(UOrders uOrders);

    int insertDynamic(UOrders uOrders);

    int updateDynamic(UOrders uOrders);

    UOrders selectById(Long id);

    List<UOrders> selectDynamic(UOrders uOrders);
}