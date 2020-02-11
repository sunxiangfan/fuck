package com.fuck.merchant.controller;

import com.alibaba.fastjson.JSON;
import com.fuck.core.dto.LoginDTO;
import com.fuck.core.dto.RegisterDTO;
import com.fuck.core.entity.User;
import com.fuck.core.entity.MOrders;
import com.fuck.core.result.ResultVO;
import com.fuck.core.service.MOrdersService;
import com.fuck.core.service.UserService;
import com.fuck.merchant.config.Number62;
import com.fuck.merchant.config.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Base64Utils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class UserController {

    private static final String md5 = "@R)(*EH@";

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MOrdersService mOrdersService;
    @Autowired
    private UserService userService;

    @PostMapping("createMOrders")
    public ResultVO createMOrders(@RequestBody MOrders mOrders) {
        log.info("createMOrders start:" + JSON.toJSONString(mOrders));
        try {
            mOrdersService.createMOrders(mOrders);
            return ResultVO.success();
        } catch (Exception e) {
            log.error("createMOrders error", e);
            return ResultVO.fail();
        }
    }

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

    @PostMapping(value = "/test")
    public ResultVO test(@RequestBody User user) {

        return ResultVO.fail();
    }

    public static void main(String[] args) throws InterruptedException {
//        String str = "13111110495";
//        String pwd = "jfg204gh24g20gh024jfg203fj20g";
//        String type = "1";
//
//
//        String token = "eyJhbGciOiJIUzI1NiIsIlR5cGUiOiJKd3QiLCJ0eXAiOiJKV1QifQ.eyJ0ZWwiOiIxMzExMTExMDQ5NSIsInB3ZCI6ImpmZzIwNGdoMjRnMjBnaDAyNGpmZzIwM2ZqMjBnIiwidHlwZSI6IjEiLCJleHAiOjE1Nzk3NjY5MTJ9.Ktp1eoxIiVCS0DfPCz6gkwPt-8v-kRIZWQFych0CcYs";
//
//            Thread.sleep(1000);
//            //String token = TokenUtil.sign(str, pwd, type);
//
//            System.out.println( 3600 * 24 * 7 * 1000);
//
//            System.out.println(TokenUtil.verify(token));


        System.out.println(Number62.encoding(131111104952L));


    }
}