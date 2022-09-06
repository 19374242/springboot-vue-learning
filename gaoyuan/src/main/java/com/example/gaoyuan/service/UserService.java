package com.example.gaoyuan.service;

import com.example.gaoyuan.common.Result;
import com.example.gaoyuan.entity.User;
import com.example.gaoyuan.entity.UserLogin;
import com.example.gaoyuan.mapper.UserMapper;
import com.example.gaoyuan.utils.TokenUtil;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service  //意思是将其注入spring容器中
public class UserService {
    @Autowired
    private UserMapper userMapper;


    public int save(User user){
//        插入
        if(user.getId()==null){ //要求id是Integer而不是int
            return userMapper.insert(user);
        }
//      更新
        else{
            return userMapper.update(user);
        }

    }

    public Integer deleteBatchById(List<Integer> ids) {
        for(int i=0;i<ids.size();i++){
            userMapper.deleteById(ids.get(i));
        }
        return 1;
    }
//登录
    public Result login(UserLogin userLogin) {
        String username=userLogin.getUsername();
        String password=userLogin.getPassword();
        if(username.length()==0||password.length()==0){
            return Result.resultDefine(0,"用户名或密码为空",null);
        }
//        查不到直接返回null，若没有这句话则会报错，因为null不能直接.equals
        if(userMapper.returnPassword(username)==null){
            return Result.resultDefine(0,"未找到用户名",null);
        }
        if(userMapper.returnPassword(username).equals(password)){
            UserLogin returnPerson=userMapper.returnAllUserData(username);

//            设置token
            String token=TokenUtil.createToken(username,password);
            returnPerson.setToken(token);

            return Result.resultDefine(1,"返回成功",returnPerson);
        }
        return Result.resultDefine(0,"密码错误",null);
    }

    public boolean register(UserLogin userRegister) {
        String username=userRegister.getUsername();
        String password=userRegister.getPassword();
        if(username.length()==0||password.length()==0){
            return false;
        }
        else if(userMapper.returnPassword(username)!=null){
            return false;
        }
        else {
            log.info("1111");
            userMapper.register(username,password);
        }

        return true;
    }
//用户个人信息修改
    public UserLogin alter(UserLogin userRegister) {
        userMapper.updatePersonInfo(userRegister);
        return userMapper.returnAllUserData(userRegister.getUsername());
    }
}
