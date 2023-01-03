package com.auggie.student_server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentServerApplicationTestsTest {

    private StudentServerApplicationTests studentServerApplicationTestsUnderTest;

    @BeforeEach
    void setUp() {
        studentServerApplicationTestsUnderTest = new StudentServerApplicationTests();
    }

    @Test
    void testContextLoads() {
        // Setup
        // Run the test
        studentServerApplicationTestsUnderTest.contextLoads();

        // Verify the results
    }

    @Test
    void testTestMain() {
        // Setup
        // Run the test
        studentServerApplicationTestsUnderTest.testMain();

        // Verify the results
    }
}
