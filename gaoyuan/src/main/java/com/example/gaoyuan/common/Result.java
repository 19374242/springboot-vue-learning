package com.example.gaoyuan.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
//有参构造
@AllArgsConstructor
public class Result {
//    0失败,1成功
    private Integer code;
//    登录失败原因
    private String message;
//    携带数据
    private Object data;
    public static Result resultDefine(Integer code,String msg,Object data){
        return new Result(code,msg,data);
    }

}
