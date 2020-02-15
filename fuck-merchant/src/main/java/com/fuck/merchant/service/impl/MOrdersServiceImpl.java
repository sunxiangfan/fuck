package com.fuck.merchant.service.impl;

import com.fuck.merchant.entity.MOrders;
import com.fuck.merchant.entity.User;
import com.fuck.merchant.mapper.MOrdersMapper;
import com.fuck.merchant.mapper.UserMapper;
import com.fuck.merchant.result.ResultVO;
import com.fuck.merchant.service.MOrdersService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MOrdersServiceImpl implements MOrdersService {

    @Autowired
    private MOrdersMapper mOrdersMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public ResultVO createMOrders(MOrders mOrders) {

        userMapper.freezeMoney(mOrders.getUserId(), mOrders.getMMoney());


        mOrdersMapper.insert(mOrders);

        return ResultVO.success();
    }

    @Override
    public void updateMOrders(MOrders mOrders) {

        mOrdersMapper.updateDynamic(mOrders);
    }

    @Override
    public PageInfo<MOrders> queryMOrders(MOrders mOrders) {
        PageHelper.startPage(mOrders.getPageNum(), mOrders.getPageSize());
        List<MOrders> ms = mOrdersMapper.selectDynamic(mOrders);
        PageInfo<MOrders> pageInfo = new PageInfo<MOrders>(ms);
        return pageInfo;
    }

    @Override
    public MOrders queryMOrdersByNo(String mOrdersNo) {
        return null;
    }
}