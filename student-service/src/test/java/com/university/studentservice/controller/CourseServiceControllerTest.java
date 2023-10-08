package com.university.studentservice.controller;

import com.university.studentservice.client.CourseServiceClient;
import com.university.studentservice.response.CourseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
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
        List<CourseResponse> courseResponses = Collections.singletonList(new CourseResponse());

        when(courseServiceClient.getCourses()).thenReturn(courseResponses);

        List<CourseResponse> result = courseServiceController.getAvailableCourses();

        verify(courseServiceClient).getCourses();

        assertEquals(courseResponses, result);
    }

}