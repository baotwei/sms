package com.auggie.student_server.controller;

import com.auggie.student_server.entity.Student;
import com.auggie.student_server.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;



@RestController
@CrossOrigin("*")
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/addStudent")
    public boolean addStudent(@RequestBody Student student) {
        System.out.println("正在保存学生对象" + student);
        return studentService.save(student);
    }

    @PostMapping("/login")
    public boolean login(@RequestBody Student student) {
        System.out.println("正在验证学生登陆 " + student);
        Student s = studentService.findById(student.getSno());
        if (s == null || !s.getPassword().equals(student.getPassword())) {
            return false;
        }
        else {
            return true;
        }
    }

    @PostMapping("/findBySearch")
    public List<Student> findBySearch(@RequestBody Student student) {
        Integer fuzzy = (student.getPassword() == null) ? 0 : 1;
        return studentService.findBySearch(student.getSno(), student.getSname(), fuzzy);
    }

    @GetMapping("/findById/{sno}")
    public Student findById(@PathVariable("sno") String sno) {
        System.out.println("正在查询学生信息 By sno " + sno);
        return studentService.findById(sno);
    }

    @GetMapping("/findByPage/{page}/{size}")
    public List<Student> findByPage(@PathVariable("page") int page, @PathVariable("size") int size) {
        System.out.println("查询学生列表分页 " + page + " " + size);
        return studentService.findByPage(page, size);
    }

    @GetMapping("/getLength")
    public Integer getLength() {
        return studentService.getLength();
    }

    @GetMapping("/deleteById/{sno}")
    public boolean deleteById(@PathVariable("sno") String sno) {
        System.out.println("正在删除学生 sid：" + sno);
        return studentService.deleteById(sno);
    }

    @PostMapping("/updateStudent")
    public boolean updateStudent(@RequestBody Student student) {
        System.out.println("更新 " + student);
        return studentService.updateById(student);
    }

    @PostMapping("/updateStudentBySno")
    public boolean updateStudentBySno(@RequestBody Student student) {
        System.out.println("更新 " + student);
        return studentService.updateBySno(student);
    }
}
