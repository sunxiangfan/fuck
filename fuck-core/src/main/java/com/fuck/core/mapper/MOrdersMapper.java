package com.fuck.core.mapper;

import com.fuck.core.entity.MOrders;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface MOrdersMapper {

    int delete(Integer id);

    int insert(MOrders mOrders);

    int insertDynamic(MOrders mOrders);

    int updateDynamic(MOrders mOrders);

    int update(MOrders mOrders);

    MOrders selectById(Integer id);

    List<MOrders> selectDynamic(MOrders mOrders);
}