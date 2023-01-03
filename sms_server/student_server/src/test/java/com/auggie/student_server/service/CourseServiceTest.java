package com.auggie.student_server.service;

import com.auggie.student_server.entity.Course;
import com.auggie.student_server.mapper.CourseMapper;
import com.auggie.student_server.mapper.MajorCourseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseMapper mockCourseMapper;
    @Mock
    private MajorCourseMapper mockMajorCourseMapper;

    @InjectMocks
    private CourseService courseServiceUnderTest;

    @Test
    void testFindBySearch1() {
        // Setup
        final Map<String, String> map = new HashMap<>();
        final List<Course> expectedResult = Arrays.asList(new Course(0, "cname", 0, "mname", 0));

        // Configure CourseMapper.findBySearch(...).
        final List<Course> courses = Arrays.asList(new Course(0, "cname", 0, "mname", 0));
        when(mockCourseMapper.findBySearch(0, "cname", 0, 0, 0)).thenReturn(courses);

        // Run the test
        final List<Course> result = courseServiceUnderTest.findBySearch(map);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFindBySearch1_CourseMapperReturnsNoItems() {
        // Setup
        final Map<String, String> map = new HashMap<>();
        when(mockCourseMapper.findBySearch(0, "cname", 0, 0, 0)).thenReturn(Collections.emptyList());

        // Run the test
        final List<Course> result = courseServiceUnderTest.findBySearch(map);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testFindBySearch2() {
        // Setup
        final List<Course> expectedResult = Arrays.asList(new Course(0, "cname", 0, "mname", 0));

        // Configure CourseMapper.findBySearch(...).
        final List<Course> courses = Arrays.asList(new Course(0, "cname", 0, "mname", 0));
        when(mockCourseMapper.findBySearch(0, "cname", 0, 0, 0)).thenReturn(courses);

        // Run the test
        final List<Course> result = courseServiceUnderTest.findBySearch(0);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFindBySearch2_CourseMapperReturnsNoItems() {
        // Setup
        when(mockCourseMapper.findBySearch(0, "cname", 0, 0, 0)).thenReturn(Collections.emptyList());

        // Run the test
        final List<Course> result = courseServiceUnderTest.findBySearch(0);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testFindById() {
        // Setup
        final List<Course> expectedResult = Arrays.asList(new Course(0, "cname", 0, "mname", 0));

        // Configure CourseMapper.findBySearch(...).
        final List<Course> courses = Arrays.asList(new Course(0, "cname", 0, "mname", 0));
        when(mockCourseMapper.findBySearch(0, "cname", 0, 0, 0)).thenReturn(courses);

        // Run the test
        final List<Course> result = courseServiceUnderTest.findById(0);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFindById_CourseMapperReturnsNoItems() {
        // Setup
        when(mockCourseMapper.findBySearch(0, "cname", 0, 0, 0)).thenReturn(Collections.emptyList());

        // Run the test
        final List<Course> result = courseServiceUnderTest.findById(0);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testUpdateById() {
        // Setup
        final Course course = new Course(0, "cname", 0, "mname", 0);
        when(mockCourseMapper.updateById(new Course(0, "cname", 0, "mname", 0))).thenReturn(false);

        // Run the test
        final boolean result = courseServiceUnderTest.updateById(course);

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    void testInsertCourse() {
        // Setup
        final Course course = new Course(0, "cname", 0, "mname", 0);
        when(mockCourseMapper.insertCourse(new Course(0, "cname", 0, "mname", 0))).thenReturn(false);
        when(mockCourseMapper.findByCname("cname")).thenReturn(new Course(0, "cname", 0, "mname", 0));

        // Run the test
        final boolean result = courseServiceUnderTest.insertCourse(course);

        // Verify the results
        assertThat(result).isFalse();
        verify(mockMajorCourseMapper).insertCourse(0, 0);
    }

    @Test
    void testDeleteById() {
        // Setup
        when(mockCourseMapper.deleteById(0)).thenReturn(false);

        // Run the test
        final boolean result = courseServiceUnderTest.deleteById(0);

        // Verify the results
        assertThat(result).isFalse();
    }
}
