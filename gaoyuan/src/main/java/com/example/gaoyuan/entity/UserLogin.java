package com.example.gaoyuan.entity;

import lombok.Data;

@Data
public class UserLogin {
    private String username;
    private String password;
    private String nickname;
//    头像
    private String avatar;
    private String token;
}
