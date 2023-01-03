package com.auggie.student_server.service;

import com.auggie.student_server.entity.Course;
import com.auggie.student_server.entity.CourseTeacher;
import com.auggie.student_server.entity.CourseTeacherInfo;
import com.auggie.student_server.mapper.CourseTeacherMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseTeacherServiceTest {

    @Mock
    private CourseTeacherMapper mockCourseTeacherMapper;

    @InjectMocks
    private CourseTeacherService courseTeacherServiceUnderTest;

    @Test
    void testInsertCourseTeacher() {
        // Setup
        when(mockCourseTeacherMapper.insertCourseTeacher(0, 0, "term")).thenReturn(false);

        // Run the test
        final boolean result = courseTeacherServiceUnderTest.insertCourseTeacher(0, 0, "term");

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    void testFindMyCourse() {
        // Setup
        final List<Course> expectedResult = Arrays.asList(new Course(0, "cname", 0, "mname", 0));

        // Configure CourseTeacherMapper.findMyCourse(...).
        final List<Course> courses = Arrays.asList(new Course(0, "cname", 0, "mname", 0));
        when(mockCourseTeacherMapper.findMyCourse(0, "term")).thenReturn(courses);

        // Run the test
        final List<Course> result = courseTeacherServiceUnderTest.findMyCourse(0, "term");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFindMyCourse_CourseTeacherMapperReturnsNoItems() {
        // Setup
        when(mockCourseTeacherMapper.findMyCourse(0, "term")).thenReturn(Collections.emptyList());

        // Run the test
        final List<Course> result = courseTeacherServiceUnderTest.findMyCourse(0, "term");

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }



    @Test
    void testFindBySearch1() {
        // Setup
        final List<CourseTeacher> expectedResult = Arrays.asList(new CourseTeacher(0, 0, 0, "term"));

        // Configure CourseTeacherMapper.findBySearch(...).
        final List<CourseTeacher> courseTeachers = Arrays.asList(new CourseTeacher(0, 0, 0, "term"));
        when(mockCourseTeacherMapper.findBySearch(0, 0, "term")).thenReturn(courseTeachers);

        // Run the test
        final List<CourseTeacher> result = courseTeacherServiceUnderTest.findBySearch(0, 0, "term");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFindBySearch1_CourseTeacherMapperReturnsNoItems() {
        // Setup
        when(mockCourseTeacherMapper.findBySearch(0, 0, "term")).thenReturn(Collections.emptyList());

        // Run the test
        final List<CourseTeacher> result = courseTeacherServiceUnderTest.findBySearch(0, 0, "term");

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }




    @Test
    void testDeleteById() {
        // Setup
        final CourseTeacher courseTeacher = new CourseTeacher(0, 0, 0, "term");
        when(mockCourseTeacherMapper.deleteById(new CourseTeacher(0, 0, 0, "term"))).thenReturn(false);

        // Run the test
        final boolean result = courseTeacherServiceUnderTest.deleteById(courseTeacher);

        // Verify the results
        assertThat(result).isFalse();
    }
}
