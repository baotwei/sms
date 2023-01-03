package com.auggie.student_server.service;

import com.auggie.student_server.entity.Student;
import com.auggie.student_server.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private MajorService majorService;

    @Autowired
    private SCTService sctService;

    public List<Student> findByPage(Integer num, Integer size) {
        // num：第几页，size：一页多大
        // num：从零开始
        List<Student> studentList = studentMapper.findAll();
        ArrayList<Student> list = new ArrayList<Student>();

        int start = size * num;
        int end = size * (num + 1);
        int sz = studentList.size();

        for (int i = start; i < end && i < sz; i++) {
            list.add(studentList.get(i));
        }

        return list;
    }

    public List<Student> findBySearch(String sno, String sname, Integer fuzzy) {
        Student student = new Student();
        student.setSno(sno);
        student.setSname(sname);
        fuzzy = (fuzzy == null) ? 0 : fuzzy;

        System.out.println();

        return studentMapper.findBySearch(student, fuzzy);
    }

    public Integer getLength() {
        return studentMapper.findAll().size();
    }

    private Integer getParentId(Integer pid){
        return majorService.getMajorIdList(pid);
    }

    public Student findById(String sno) {
        Student student=studentMapper.findById(sno);
        ArrayList<Integer> majorIdList=new ArrayList<Integer>();
        Integer p1 =getParentId(student.getMnumber());
        Integer p2=getParentId(p1);
        majorIdList.add(p2);
        majorIdList.add(p1);
        majorIdList.add(student.getMnumber());
        student.setMajorIdList(majorIdList);
        return student;
    }

    public boolean updateById(Student student) {
        if (student.getBirthday()!=null){
            student.setBirthday(student.getBirthday().substring(0,10));
        }

        return studentMapper.updateById(student);
    }

    public boolean save(Student student) {
        Integer lastSid=findLastSid().getSid()+1;
        String code;
        if(lastSid<10){
            code = "00"+lastSid;
        }else if(lastSid<100){
            code = "0"+lastSid;
        } else {
            code =""+lastSid;
        }
        String sno="213233"+code;
        student.setSno(sno);
        return studentMapper.save(student);
    }

    public boolean deleteById(String sno) {
        sctService.deleteBySid(studentMapper.findById(sno).getSid());
        return studentMapper.deleteById(sno);
    }

    public Student findLastSid(){
        return studentMapper.findLastSid();
    }


    public boolean updateBySno(Student student) {
        if (student.getBirthday()!=null){
            student.setBirthday(student.getBirthday().substring(0,10));
        }

        return studentMapper.updateBySno(student);
    }
}
