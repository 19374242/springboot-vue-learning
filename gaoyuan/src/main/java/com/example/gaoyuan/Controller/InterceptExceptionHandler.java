package com.example.gaoyuan.Controller;

import com.example.gaoyuan.common.Result;
import com.example.gaoyuan.entity.InterceptException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
//拦截到相应异常，并作为controller返回值给前端

@ControllerAdvice
@Slf4j
public class InterceptExceptionHandler {

    @ResponseBody
//    要拦截的自定义异常
    @ExceptionHandler(value=InterceptException.class)
    public Object handleException(InterceptException e) {
        String msg = e.getMsg();
        return Result.resultDefine(1234, msg, null);
    }
}
