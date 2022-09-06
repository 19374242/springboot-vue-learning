package com.example.gaoyuan.mapper;
//mapper文件夹下均为接口

import com.example.gaoyuan.entity.User;
import com.example.gaoyuan.entity.UserLogin;
import org.apache.ibatis.annotations.*;

import java.util.List;

//查询用户数据
@Mapper   //注意注解,用于告诉springboot框架此接口的实现类由Mybatis负责创建,并将其实现类对象存储到容器中
public interface UserMapper {
//    意思是该方法执行上面的sql语句
    @Select("select * from userdatabase")
    List<User> findAll();

//    注意插入数据写法为#{},意思是使用user内的数据,注意没有修改主键的话要设置主键自增
    @Insert("insert into userdatabase(username,password,nickname,email,phone,address) values(#{username},#{password},#{nickname},#{email},#{phone},#{address})")
    Integer insert(User user);

//    这里的id是数据库中id,#{id}为user中id,与/resources/mapper/User.xml二选一
//    @Update("update userdatabase set username=#{username},password=#{password},nickname=#{nickname},email=#{email},phone=#{phone},address=#{address} where id=#{id}")
    int update(User user);

    @Delete("delete from userdatabase where id=#{id}")
    Integer deleteById(@Param("id") Integer id);//@Param中参数名字和#{}中一一对应

//    分页查询和模糊查询
    @Select("select * from userdatabase where username like #{username} limit #{startNum},#{pageSize}")
    List<User> selectPage(Integer startNum, Integer pageSize,String username);

//    总条数
    @Select("select count(*) from userdatabase where username like #{username}")
    Integer selectTotal(String username);

//    登录
    @Select("select password from userrecord where username like #{username} ")
    String returnPassword(String username);

//    查出用户所有信息(要用实体类进行接收，否则只返回第一个数据)
    @Select("select * from userrecord where username like #{username} ")
    UserLogin returnAllUserData(String username);
//    注册
    @Select("insert into userrecord(username,password) values(#{username},#{password})")
    void register(String username,String password);
//    修改个人信息
    int updatePersonInfo(UserLogin userLogin);

}
