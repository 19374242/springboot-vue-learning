package com.example.gaoyuan.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

//拦截器异常
@Data
@Slf4j
public class InterceptException extends RuntimeException{
    private String msg;
    public InterceptException(String s){
        this.msg=s;
    }
}
