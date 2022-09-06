package com.example.gaoyuan.common.intercept;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.gaoyuan.common.Result;
import com.example.gaoyuan.entity.InterceptException;
import com.example.gaoyuan.entity.UserLogin;
import com.example.gaoyuan.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//拦截器
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws InterceptException {

        String token=request.getHeader("token");//前端传过来的token
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
//        血泪教训，这里不能写token.equals("")，因为null.equals会抛出异常，刚好就出问题
        if (token==null) {
            throw new InterceptException("token不存在，请重新登录");
        }
        // 获取 token 中的 username
        String username;
        try {
            username = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new InterceptException("token不存在，请重新登录");
        }
//        是否存在
        UserLogin user = userMapper.returnAllUserData(username);
        if (user == null) {
            throw new InterceptException("token不存在，请重新登录");
        }
        // 用户密码验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new InterceptException("token验证失败，请重新登录");
        }
        return true;
    }
}

