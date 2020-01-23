package com.fuck.merchant.config;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TokenUtil {

    @Autowired
    private static RedisTemplate redisTemplate;


    private static final long EXPIRE_TIME = 3600 * 24 * 7 * 1000;
    private static final String TOKEN_SECRET = "3jf024n4ng024gn0230invg30igjv03w49g0349g03jgvi2034gnv3vo3rlm4nl2nvndlwgmnbjndskfl";


    /**
     * 生成签名，15分钟过期
     *
     * @param **username**
     * @param **password**
     * @return
     */
    public static String sign(String username, String password, String type) {
        try {
            // 设置过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            // 私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            // 设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("Type", "Jwt");
            header.put("alg", "HS256");
            // 返回token字符串
            return JWT.create()
                    .withHeader(header)
                    .withClaim("tel", username)
                    .withClaim("pwd", password)
                    .withClaim("type", type)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 检验token是否正确
     *
     * @param **token**
     * @return
     */
    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}