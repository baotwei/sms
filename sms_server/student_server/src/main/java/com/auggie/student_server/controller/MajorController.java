package com.auggie.student_server.controller;

import com.auggie.student_server.entity.Major;
import com.auggie.student_server.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/major")
public class MajorController {
    @Autowired
    private MajorService majorService;

    @GetMapping("/findById/{mid}")
    public Major findById(@PathVariable Integer mid) {
        return majorService.findById(mid) ;
    }

    @GetMapping("/getMajorName/{mid}")
    public String getMajorName(@PathVariable Integer mid) {
        return majorService.getMajorName(mid);
    }

    @GetMapping("/getMajorList")
    public List<Major> getMajorList() {
        return majorService.getMajorList();
    }

    @GetMapping("/getMajorIdList/{mid}")
    private Integer getMajorIdList(@PathVariable Integer mid){
        return majorService.getMajorIdList(mid);
    }

}
