package com.fuck.merchant.service;

import com.fuck.merchant.entity.UOrders;
import com.fuck.merchant.result.ResultVO;
import com.github.pagehelper.PageInfo;

public interface UOrdersService {

    ResultVO createUOrders(UOrders uOrders);

    void updateUOrders(UOrders uOrders);

    PageInfo<UOrders> queryUOrders(UOrders uOrders);
}