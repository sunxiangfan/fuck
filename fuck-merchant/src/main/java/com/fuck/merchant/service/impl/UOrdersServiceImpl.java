package com.fuck.merchant.service.impl;

import com.fuck.merchant.entity.MOrders;
import com.fuck.merchant.entity.UOrders;
import com.fuck.merchant.mapper.MOrdersMapper;
import com.fuck.merchant.mapper.UOrdersMapper;
import com.fuck.merchant.mapper.UserMapper;
import com.fuck.merchant.result.ResultVO;
import com.fuck.merchant.service.UOrdersService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UOrdersServiceImpl implements UOrdersService {

    @Autowired
    private MOrdersMapper mOrdersMapper;
    @Autowired
    private UOrdersMapper uOrdersMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    @Transactional
    public ResultVO createUOrders(UOrders uOrders) {

        MOrders m = mOrdersMapper.selectById(uOrders.getMId());

        if (!m.getMStatus().equals("1")) {
            return ResultVO.fail("订单状态不合法，请刷新后再试");
        }

        if (m.getCreateTime().getTime() / 1000 + m.getMExpirationTime() < System.currentTimeMillis() / 1000) {
            return ResultVO.fail("订单已过期");
        }

        MOrders mOrders = new MOrders();
        mOrders.setId(uOrders.getMId());
        mOrders.setMStatus("2");
        mOrdersMapper.updateDynamic(mOrders);

        uOrdersMapper.insertDynamic(uOrders);
        return ResultVO.success();
    }

    @Override
    public void updateUOrders(UOrders uOrders) {

    }

    @Override
    public PageInfo<UOrders> queryUOrders(UOrders uOrders) {
        PageHelper.startPage(uOrders.getPageNum(), uOrders.getPageSize());
        List<UOrders> us = uOrdersMapper.selectDynamic(uOrders);
        PageInfo<UOrders> pageInfo = new PageInfo<UOrders>(us);
        return pageInfo;
    }
}