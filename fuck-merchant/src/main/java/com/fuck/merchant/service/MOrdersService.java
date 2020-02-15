package com.fuck.merchant.service;

import com.fuck.merchant.entity.MOrders;
import com.fuck.merchant.entity.User;
import com.fuck.merchant.result.ResultVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface MOrdersService {

    ResultVO createMOrders(MOrders mOrders);

    void updateMOrders(MOrders mOrders);

    PageInfo<MOrders> queryMOrders(MOrders mOrders);

    MOrders queryMOrdersByNo(String mOrdersNo);
}