package com.example.gaoyuan.Controller;

import com.example.gaoyuan.common.Result;
import com.example.gaoyuan.entity.User;
import com.example.gaoyuan.entity.UserLogin;
import com.example.gaoyuan.mapper.UserMapper;
import com.example.gaoyuan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
//此处不能使用@Controller，@RestController更多的用来返回相应格式的数据
@RestController
@RequestMapping("/user")  //给接口加前缀，要在/user处访问才行
public class UserController {
    //注入该组件
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

//    查找所有
    @GetMapping("/")   //最后还得加一个/
    public List<User> searchALL(){
        return userMapper.findAll();
    }

//    插入
    @PostMapping("/")
    //        @RequestBody意思是可以把数据整合为对象，可以有数据为null
    public Integer save(@RequestBody User user){
//        新增或者更新
        return userService.save(user);

    }
//    删
    @DeleteMapping("/{id}") //下方的id取值从上方{id}里来，名字必须相同
    public Integer delete(@PathVariable Integer id){
        return userMapper.deleteById(id);
    }

//    分页查询,返回数据与总条数
    @GetMapping("/page")  //接口路径/user/page
//    @RequestParam接受  ?pageNum=1&pageSize=10&username=s,值直接映射,参数名必须与接口路径中名字完全一致
    public HashMap<String, Object> findPage(@RequestParam Integer pageNum,
                                            @RequestParam Integer pageSize,
                                            @RequestParam String username){
//        sql语句中limit第一个参数是指第几个开始（pageNum-1）*pageSize，第二个参数是pageSize
        Integer startNum=(pageNum-1)*pageSize;
        username="%"+username+"%";//意思是只要有一个字符相等就行（%可为0~∞）
        List<User> data= userMapper.selectPage(startNum,pageSize,username);
        Integer num = userMapper.selectTotal(username);
        HashMap<String,Object> res=new HashMap<>();
        res.put("data",data);
        res.put("num",num);
        return res;
    }

//    批量删除,@RequestBody是把数据转化为java对象
    @PostMapping("/delBatch")
    public Integer deleteBatch(@RequestBody List<Integer> ids){
        return userService.deleteBatchById(ids);
    }

//登录
    @PostMapping("/login")
    public Result login(@RequestBody UserLogin userLogin){
        return userService.login(userLogin);
    }
//    注册
    @PostMapping("/register")
    public boolean register(@RequestBody UserLogin userRegister){
        return userService.register(userRegister);
    }
//    用户个人信息修改
    @PostMapping("/alter")
    public UserLogin alter(@RequestBody UserLogin userRegister){
        return userService.alter(userRegister);
    }
}
