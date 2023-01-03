package com.auggie.student_server.mapper;

import com.auggie.student_server.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
@Mapper
public interface TeacherMapper {
    //    select
    public List<Teacher> findAll();

    public Teacher findByTno(@Param("tno") String tno);

    public List<Teacher> findBySearch(@Param("tno") Integer tno, @Param("tname") String tname, @Param("fuzzy") Integer fuzzy);

    //    update
    public boolean updateById(@Param("teacher") Teacher teacher);

    //    insert
    public boolean save(@Param("teacher") Teacher teacher);

    //    delete
    public boolean deleteById(@Param("tid") Integer tid);

    public Teacher findLastTid();

    public Teacher findByTid(@Param("tid") Integer tid);
}
