package com.auggie.student_server.mapper;

import com.auggie.student_server.entity.CourseTeacherInfo;
import com.auggie.student_server.entity.SCTInfo;
import com.auggie.student_server.entity.StudentCourseTeacher;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface StudentCourseTeacherMapper {

    public List<CourseTeacherInfo> findByStudentId(@Param("sid") Integer sid,
                                                   @Param("term") String term);

    public List<SCTInfo> findBySearch(@Param("sno") Integer sno,
                                      @Param("sname") String sname,
                                      @Param("sFuzzy") Integer sFuzzy,
                                      @Param("cid") Integer cid,
                                      @Param("cname") String cname,
                                      @Param("cFuzzy") Integer cFuzzy,
                                      @Param("tno") Integer tno,
                                      @Param("tname") String tname,
                                      @Param("tFuzzy") Integer tFuzzy,
                                      @Param("lowBound") Integer lowBound,
                                      @Param("highBound") Integer highBound,
                                      @Param("term") String term);

    @Select("SELECT DISTINCT sct.term FROM studentms.sct sct where sct.state=1")
    public List<String> findAllTerm();

    @Select("SELECT * FROM studentms.sct WHERE sid = #{sct.sid} AND cid = #{sct.cid} AND tid = #{sct.tid} AND term = #{sct.term} and state=1")
    public List<StudentCourseTeacher> findBySCT(@Param("sct") StudentCourseTeacher studentCourseTeacher);

    @Insert("INSERT INTO studentms.sct (sid, cid, tid, term,state) VALUES (#{s.sid}, #{s.cid}, #{s.tid}, #{s.term},1)")
    public boolean insert(@Param("s")StudentCourseTeacher studentCourseTeacher);

    @Update("UPDATE studentms.sct SET sct.grade = #{grade} WHERE sct.state=1 and sct.sid = #{sid} AND sct.tid = #{tid} AND sct.cid = #{cid} AND sct.term = #{term}")
    public boolean updateById(@Param("sid") Integer sid,
                              @Param("cid") Integer cid,
                              @Param("tid") Integer tid,
                              @Param("term") String term,
                              @Param("grade") Integer grade);

    //    @Delete("DELETE FROM studentms.sct WHERE sid = #{sct.sid} AND tid = #{sct.tid} AND cid = #{sct.cid}")
    @Update("update studentms.sct set state=0  WHERE sid = #{sct.sid} AND tid = #{sct.tid} AND cid = #{sct.cid}")
    public boolean deleteBySCT(@Param("sct") StudentCourseTeacher sct);

    //    @Delete("DELETE FROM studentms.sct WHERE sid = #{sid}")
    @Update("update studentms.sct set state=0 WHERE sid = #{sid}")
    public boolean deleteBySid(@Param("sid") Integer sid);
}
