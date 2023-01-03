package com.auggie.student_server.service;

import com.auggie.student_server.entity.Course;
import com.auggie.student_server.mapper.CourseMapper;
import com.auggie.student_server.mapper.MajorCourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CourseService {
    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private MajorCourseMapper majorCourseMapper;

    public List<Course> findBySearch(Map<String, String> map) {
        Integer cid = null;
        Integer lowBound = null;
        Integer highBound = null;
        Integer fuzzy = 0;
        String cname = null;

        if (map.containsKey("cid")) {
            try {
                cid = Integer.parseInt(map.get("cid"));
            }
            catch (Exception e) {
            }
        }
        if (map.containsKey("lowBound")) {
            try {
                lowBound = Integer.parseInt(map.get("lowBound"));
            }
            catch (Exception e) {
            }
        }
        if (map.containsKey("highBound")) {
            try {
                highBound = Integer.valueOf(map.get("highBound"));
            }
            catch (Exception e) {
            }
        }
        if (map.containsKey("cname")) {
            cname = map.get("cname");
        }
        if (map.containsKey("fuzzy")) {
            fuzzy = (map.get("fuzzy").equals("true")) ? 1 : 0;
        }
        System.out.println("查询课程 map：" + map);
        System.out.println(cid + " " + cname + " " + fuzzy + " " + lowBound + " " + highBound);
        return courseMapper.findBySearch(cid, cname, fuzzy, lowBound, highBound);
    }

    public List<Course> findBySearch(Integer cid) {
        return courseMapper.findBySearch(cid, null, 0, null, null);
    }

    public List<Course> findById(Integer cid) {
        HashMap<String, String> map = new HashMap<>();
        if (cid != null)
            map.put("cid", String.valueOf(cid));
        return findBySearch(map);
    }

    public boolean updateById(Course course) {
        return courseMapper.updateById(course);
    }

    public boolean insertCourse(Course course) {
        boolean flg=courseMapper.insertCourse(course);
        Course course1=courseMapper.findByCname(course.getCname());
        majorCourseMapper.insertCourse(course.getMno(),course1.getCid());
        if (flg){
            return true;
        }else {
            return false;
        }

    }

    public boolean deleteById(Integer cid) {
        return courseMapper.deleteById(cid);
    }


}
