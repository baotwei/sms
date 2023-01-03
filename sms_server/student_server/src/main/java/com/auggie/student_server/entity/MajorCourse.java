package com.auggie.student_server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("MajorCourse")
public class MajorCourse {
    private Integer mcid;
    private Integer mno;
    private Integer cno;

}
