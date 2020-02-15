package com.fuck.merchant.mapper;

import com.fuck.merchant.entity.MOrders;

import java.util.List;

public interface MOrdersMapper {

    int delete(Integer id);

    int insert(MOrders mOrders);

    int insertDynamic(MOrders mOrders);

    int updateDynamic(MOrders mOrders);

    int update(MOrders mOrders);

    MOrders selectById(Long id);

    List<MOrders> selectDynamic(MOrders mOrders);
}