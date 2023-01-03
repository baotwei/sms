package com.auggie.student_server.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MajorCourseMapper {

    @Insert("INSERT INTO studentms.mc (mno, cno,state) VALUES (#{mno}, #{cno},1)")
    public void insertCourse(@Param("mno") Integer mno,
                             @Param("cno") Integer cno
                             );
}
