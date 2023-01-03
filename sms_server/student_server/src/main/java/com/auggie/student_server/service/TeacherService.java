package com.auggie.student_server.service;

import com.auggie.student_server.entity.Teacher;
import com.auggie.student_server.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@Service
public class TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private MajorService majorService;


    public List<Teacher> findBySearch(Map<String, String> map) {
        Integer tno = null;
        String tname = null;
        Integer fuzzy = null;
        if (map.containsKey("tno")) {
            try {
                tno = Integer.parseInt(map.get("tno"));
            }
            catch (Exception e) {
            }
        }
        if (map.containsKey("tname")) {
            tname = map.get("tname");
        }
        if (map.containsKey("fuzzy")) {
            fuzzy = map.get("fuzzy").equals("true") ? 1 : 0;
        }
        System.out.println(map);
        System.out.println("查询类型：" + tno  + ", " + tname + ", " + fuzzy);
        return teacherMapper.findBySearch(tno, tname, fuzzy);
    }

    public List<Teacher> findByPage(Integer num, Integer size) {
        // num：第几页，size：一页多大
        // num：从零开始
        List<Teacher> teacherList = teacherMapper.findAll();
        ArrayList<Teacher> list = new ArrayList<Teacher>();

        int start = size * num;
        int end = size * (num + 1);
        int sz = teacherList.size();

        for (int i = start; i < end && i < sz; i++) {
            list.add(teacherList.get(i));
        }

        return list;
    }

    public Integer getLength() {
        return teacherMapper.findAll().size();
    }
    private Integer getParentId(Integer pid){
        return majorService.getMajorIdList(pid);
    }

    public Teacher findByTno(String tno) {
        Teacher teacher=teacherMapper.findByTno(tno);
        ArrayList<Integer> majorIdList=new ArrayList<Integer>();
        Integer p1 =getParentId(teacher.getMnumber());
        Integer p2=getParentId(p1);
        majorIdList.add(p2);
        majorIdList.add(p1);
        majorIdList.add(teacher.getMnumber());
        teacher.setMajorIdList(majorIdList);

        return teacher;
    }

    public boolean updateById(Teacher teacher) {
        System.out.println(teacher);
        if (teacher.getBirthday()!=null){
            teacher.setBirthday(teacher.getBirthday().substring(0,10));
        }
        System.out.println(teacher);
        return teacherMapper.updateById(teacher);
    }

    public boolean save(Teacher teacher) {
        Integer lastTid=findLastTid().getTid()+1;
        String code;
        if(lastTid<10){
            code = "00"+lastTid;
        }else if(lastTid<100){
            code = "0"+lastTid;
        } else {
            code =""+lastTid;
        }
        String tno="213232"+code;
        teacher.setTno(tno);
        return teacherMapper.save(teacher);
    }

    public boolean deleteById(Integer tid) {
        return teacherMapper.deleteById(tid);
    }
    public Teacher findLastTid(){
        return teacherMapper.findLastTid();
    }

    public Teacher findByTid(Integer tid) {
        return teacherMapper.findByTid(tid);
    }
}
