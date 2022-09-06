package com.example.gaoyuan.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class TokenUtil {

//    生成token
    private static final long EXPIRE_TIME = 5 * 60 * 1000;
    public static String createToken(String username,String password){
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        String token="";
        token= JWT.create().withAudience(username) // 将 username保存到 token 里面
                .withExpiresAt(date) //五分钟后token过期
                .sign(Algorithm.HMAC256(password)); // 以 password 作为 token 的密钥
        return token;

    }
}
