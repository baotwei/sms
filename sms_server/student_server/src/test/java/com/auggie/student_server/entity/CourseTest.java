package com.auggie.student_server.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CourseTest {

    private Course courseUnderTest;

    @BeforeEach
    void setUp() {
        courseUnderTest = new Course(0, "cname", 0, "mname", 0);
    }

    @Test
    void testEquals() {
        assertThat(courseUnderTest.equals("o")).isFalse();
    }

    @Test
    void testCanEqual() {
        assertThat(courseUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() {
        assertThat(courseUnderTest.hashCode()).isEqualTo(2117329373);
    }

    @Test
    void testToString() {
        assertThat(courseUnderTest.toString()).isEqualTo("Course(cid=0, cname=cname, ccredit=0, mname=mname, mno=0)");
    }
}
