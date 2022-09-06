package com.example.gaoyuan.mapper;

import com.example.gaoyuan.entity.Files;
import com.example.gaoyuan.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("insert into filetable(id,url,name,isexists) values(#{id},#{url},#{name},#{isexists})")
    Integer insertFile(Files files);

    @Select("select * from filetable")
    List<Files> findAll();

    @Select("select url from filetable where id=#{id}")
    String getUrl(Integer id);
}
