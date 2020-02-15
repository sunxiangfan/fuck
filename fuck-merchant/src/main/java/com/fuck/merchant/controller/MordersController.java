package com.fuck.merchant.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fuck.merchant.entity.MOrders;
import com.fuck.merchant.entity.User;
import com.fuck.merchant.result.ResultVO;
import com.fuck.merchant.service.MOrdersService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MordersController {

    //1商家初始化订单，2用户已接单，3商家确认完成，4商家确认失败，5已过期

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MOrdersService mOrdersService;

    @PostMapping("updateMOrders")
    public ResultVO updateMOrders(@RequestBody MOrders mOrders) {
        log.info("updateMOrders start:" + JSON.toJSONString(mOrders));
        try {
            mOrdersService.updateMOrders(mOrders);
            return ResultVO.success();
        } catch (Exception e) {
            log.error("updateMOrders error", e);
            return ResultVO.fail();
        }
    }

    @PostMapping("mOrdersList")
    public ResultVO mOrdersList(@RequestBody MOrders mOrders) {
        log.info("mOrdersList start:" + JSON.toJSONString(mOrders));
        try {
            String str = redisTemplate.opsForValue().get(mOrders.getAdmin_token()).toString();
            User user = JSONObject.parseObject(str, User.class);
            mOrders.setUserId(user.getId());
            PageInfo<MOrders> pageInfo = mOrdersService.queryMOrders(mOrders);
            return ResultVO.success(pageInfo);
        } catch (Exception e) {
            log.error("mOrdersList error", e);
            return ResultVO.fail();
        }
    }

    @PostMapping("createMOrders")
    public ResultVO createMOrders(@RequestBody MOrders mOrders) {
        log.info("createMOrders start:" + JSON.toJSONString(mOrders));
        try {
            String str = redisTemplate.opsForValue().get(mOrders.getAdmin_token()).toString();
            User user = JSONObject.parseObject(str, User.class);
            mOrders.setUserId(user.getId());
            mOrdersService.createMOrders(mOrders);
            return ResultVO.success();
        } catch (Exception e) {
            log.error("createMOrders error", e);
            return ResultVO.fail();
        }
    }
}