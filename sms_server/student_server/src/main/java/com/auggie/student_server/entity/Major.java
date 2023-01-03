package com.auggie.student_server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("Major")
public class Major {
    private Integer mid;
    private String mno;
    private String mname;
    private Integer mteacher;
    private Integer parentId;

    private List<Major> majorList;

    public Major(Integer mid, String mno, String mname, Integer mteacher, Integer parentId) {
        this.mid = mid;
        this.mno = mno;
        this.mname = mname;
        this.mteacher = mteacher;
        this.parentId = parentId;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getMno() {
        return mno;
    }

    public void setMno(String mno) {
        this.mno = mno;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public Integer getMteacher() {
        return mteacher;
    }

    public void setMteacher(Integer mteacher) {
        this.mteacher = mteacher;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<Major> getMajorList() {
        return majorList;
    }

    public void setMajorList(List<Major> majorList) {
        this.majorList = majorList;
    }
}
