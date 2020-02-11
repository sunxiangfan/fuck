package com.fuck.core.service.impl;

import com.fuck.core.entity.MOrders;
import com.fuck.core.mapper.UserMapper;
import com.fuck.core.mapper.MOrdersMapper;
import com.fuck.core.result.ResultVO;
import com.fuck.core.service.MOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MOrdersServiceImpl implements MOrdersService {

    @Autowired
    private MOrdersMapper mOrdersMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResultVO createMOrders(MOrders mOrders) {

        userMapper.freezeMoney(mOrders.getMMoney());



        mOrdersMapper.insert(mOrders);

        return null;
    }
}
