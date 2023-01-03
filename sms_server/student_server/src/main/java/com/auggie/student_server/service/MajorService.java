package com.auggie.student_server.service;

import com.auggie.student_server.entity.Major;
import com.auggie.student_server.mapper.MajorMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajorService {
    @Autowired
    private MajorMapper majorMapper;

    public Major findById(Integer mid) {
        return majorMapper.findById(mid);
    }

    public String getMajorName(Integer mid){
        Major major=findById(mid);
        System.out.println(major);
        String majorName=major.getMname();
        while (major.getParentId()!=null) {
            major= findById(major.getParentId());
            majorName=major.getMname()+"/"+majorName;
            System.out.println(major);
        }
        return majorName ;
    }

    private List<Major> getValidMajor(List<Major> majorList){
        for(int i=0;i<majorList.size();i++){
            Major major= majorList.get(i);
            if (major.getMajorList().isEmpty()){
                majorList.set(i,new Major(major.getMid(),major.getMno(),major.getMname(),major.getMteacher(),major.getParentId()));
            }else {
                getValidMajor(major.getMajorList());
            }
        }

        return majorList;
    }

    public List<Major> getMajorList() {
        List<Major> majorList=majorMapper.getMajorList();
        getValidMajor(majorList);
        return majorList;
    }

    public Integer getMajorIdList(Integer mid){
        return majorMapper.getMajorIdList(mid);
    }
}
