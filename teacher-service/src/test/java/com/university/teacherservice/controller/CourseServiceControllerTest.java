package com.university.teacherservice.controller;

import com.university.teacherservice.client.CourseServiceClient;
import com.university.teacherservice.response.CourseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CourseServiceControllerTest {

    @Mock
    private CourseServiceClient courseServiceClient;

    @InjectMocks
    private CourseServiceController courseServiceController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAvailableCourses() {
        List<CourseResponse> availableCourses = Collections.singletonList(new CourseResponse());
        when(courseServiceClient.getCourses()).thenReturn(availableCourses);

        List<CourseResponse> result = courseServiceController.getAvailableCourses();

        verify(courseServiceClient, times(1)).getCourses();
        assertNotNull(result);
        assertEquals(availableCourses, result);
    }
}