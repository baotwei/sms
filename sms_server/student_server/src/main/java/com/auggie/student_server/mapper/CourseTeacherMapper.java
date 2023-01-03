package com.auggie.student_server.mapper;

import com.auggie.student_server.entity.Course;
import com.auggie.student_server.entity.CourseTeacher;
import com.auggie.student_server.entity.CourseTeacherInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface CourseTeacherMapper {

    @Insert("INSERT INTO studentms.ct (cid, tid, term,state) VALUES (#{cid}, #{tid}, #{term},1)")
    public boolean insertCourseTeacher(@Param("cid") Integer cid,
                                       @Param("tid") Integer tid,
                                       @Param("term") String term);

    public List<CourseTeacher> findBySearch(@Param("cid") Integer cid,
                                            @Param("tid") Integer tid,
                                            @Param("term") String term);

    public List<Course> findMyCourse(@Param("tid") Integer tid,
                                     @Param("term") String term);

    public List<CourseTeacherInfo> findCourseTeacherInfo(@Param("tno") Integer tno,
                                                         @Param("tname") String tname,
                                                         @Param("tFuzzy") Integer tFuzzy,
                                                         @Param("cid") Integer cid,
                                                         @Param("cname") String cname,
                                                         @Param("cFuzzy") Integer cFuzzy);

    //    @Delete("DELETE FROM studentms.ct WHERE cid = #{c.cid} AND tid = #{c.tid}")
    @Update("update studentms.ct set state=0  WHERE cid = #{c.cid} AND tid = #{c.tid} ")
    public boolean deleteById(@Param("c") CourseTeacher courseTeacher);
}
