package com.fuck.merchant.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fuck.merchant.config.Number62;
import com.fuck.merchant.config.TokenUtil;
import com.fuck.merchant.dto.LoginDTO;
import com.fuck.merchant.dto.RegisterDTO;
import com.fuck.merchant.entity.MOrders;
import com.fuck.merchant.entity.UOrders;
import com.fuck.merchant.entity.User;
import com.fuck.merchant.result.ResultVO;
import com.fuck.merchant.service.MOrdersService;
import com.fuck.merchant.service.UOrdersService;
import com.fuck.merchant.service.UserService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class UordersController {

    //1商家初始化订单，2用户已接单，3商家确认完成，4商家确认失败，5已过期

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UOrdersService uOrdersService;

    @PostMapping("uOrdersList")
    public ResultVO uOrdersList(@RequestBody UOrders uOrders) {
        log.info("uOrdersList start:" + JSON.toJSONString(uOrders));
        try {
            String str = redisTemplate.opsForValue().get(uOrders.getAdmin_token()).toString();
            User user = JSONObject.parseObject(str, User.class);
            uOrders.setUserId(user.getId());
            PageInfo<UOrders> pageInfo = uOrdersService.queryUOrders(uOrders);
            return ResultVO.success(pageInfo);
        } catch (Exception e) {
            log.error("uOrdersList error", e);
            return ResultVO.fail();
        }
    }

    @PostMapping("createUOrders")
    public ResultVO createUOrders(@RequestBody UOrders uOrders) {
        log.info("createUOrders start:" + JSON.toJSONString(uOrders));
        try {
            String str = redisTemplate.opsForValue().get(uOrders.getAdmin_token()).toString();
            User user = JSONObject.parseObject(str, User.class);
            uOrders.setUserId(user.getId());
            return uOrdersService.createUOrders(uOrders);
        } catch (Exception e) {
            log.error("createUOrders error", e);
            return ResultVO.fail();
        }
    }
}