package com.auggie.student_server.mapper;

import com.auggie.student_server.entity.Major;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MajorMapper {

    @Select("SELECT * FROM m where mid=#{mid} and state=1")
    public Major findById(@Param("mid") Integer mid);

    public List<Major> getMajorList();


    @Select("SELECT parentId FROM m where mid=#{mid} and state=1")
    public Integer getMajorIdList(@Param("mid") Integer mid);
}
