package com.fuck.merchant.controller;

import com.alibaba.fastjson.JSON;
import com.fuck.merchant.config.Number62;
import com.fuck.merchant.config.TokenUtil;
import com.fuck.merchant.dto.LoginDTO;
import com.fuck.merchant.dto.RegisterDTO;
import com.fuck.merchant.entity.User;
import com.fuck.merchant.result.ResultVO;
import com.fuck.merchant.service.UserService;
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
public class UserController {

    //1商家初始化订单，2用户已接单，3商家确认完成，4商家确认失败，5已过期

    private static final String md5 = "@R)(*EH@";

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    public ResultVO login(@RequestBody LoginDTO dto) {
        log.info("login start:" + JSON.toJSONString(dto));
        try {
            if (null == dto.getMobile() || null == dto.getPassword() || null == dto.getType()) {
                return ResultVO.fail("参数不合法");
            }
            User user = userService.findUserByMobileAndType(dto.getMobile(), dto.getType());
            if (null == user) {
                return ResultVO.fail("用户不存在");
            }
            if (!user.getPassword().equals(DigestUtils.md5Hex(dto.getPassword() + md5))) {
                return ResultVO.fail("密码错误");
            }
            String token = TokenUtil.sign(user.getMobile(), user.getPassword(), user.getType());

            redisTemplate.opsForValue().set(token, JSON.toJSONString(user), 3600 * 24 * 7, TimeUnit.SECONDS);
            return ResultVO.success(token);
        } catch (Exception e) {
            log.error("login error", e);
            return ResultVO.fail("网络异常，请稍后再试");
        }
    }

    @PostMapping(value = "/register")
    public ResultVO register(@RequestBody RegisterDTO dto) {
        log.info("register start:" + JSON.toJSONString(dto));
        try {
            if (null == dto.getCode() || null == dto.getConfirmPassword() || null == dto.getMobile() || null == dto.getPassword() || null == dto.getType()) {
                return ResultVO.fail("参数不合法");
            }
            User user = userService.findUserByMobileAndType(dto.getMobile(), dto.getType());
            if (null != user) {
                return ResultVO.fail("用户已存在");
            }
            Object code = redisTemplate.opsForValue().get("user.register.code" + dto.getType() + "." + dto.getMobile());
            if (null == code || !code.toString().equals(dto.getCode())) {
                return ResultVO.fail("验证码不正确");
            }
            if (!dto.getPassword().equals(dto.getConfirmPassword())) {
                return ResultVO.fail("密码输入不一致");
            }
            user = new User();
            user.setMobile(dto.getMobile());
            user.setPassword(DigestUtils.md5Hex(dto.getPassword() + md5));
            user.setType(dto.getType());
            if (user.getType().equals(1)) {
                user.setMerchantno(user.getMobile());
            } else {
                user.setSuperiorCode(dto.getSuperiorCode());
                Long icode = Long.parseLong(dto.getMobile() + dto.getType());
                user.setInvitationCode(Number62.encoding(icode));
            }
            userService.insertDynamic(user);
            return ResultVO.success();
        } catch (Exception e) {
            log.error("register error", e);
            return ResultVO.fail("网络异常，请稍后再试");
        }
    }

    @PostMapping(value = "/sendMsg")
    public ResultVO sendMsg(@RequestBody LoginDTO dto) {
        log.info("sendMsg start:" + JSON.toJSONString(dto));
        try {
            if (null == dto.getMobile() || null == dto.getType()) {
                return ResultVO.fail("参数不合法");
            }
            int code = (int) ((Math.random() * 9 + 1) * 1000);
            redisTemplate.opsForValue().set("user.register.code" + dto.getType() + "." + dto.getMobile(), code, 300, TimeUnit.SECONDS);

            //TODO 发注册短信

            return ResultVO.success(code);
        } catch (Exception e) {
            log.error("sendMsg error", e);
            return ResultVO.fail("网络异常，请稍后再试");
        }
    }
}