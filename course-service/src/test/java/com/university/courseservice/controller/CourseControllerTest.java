package com.university.courseservice.controller;

import com.university.courseservice.request.CourseRequest;
import com.university.courseservice.response.CourseResponse;
import com.university.courseservice.response.Response;
import com.university.courseservice.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static com.university.courseservice.enums.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCourse() {
        CourseRequest courseRequest = new CourseRequest();
        CourseResponse courseResponse = new CourseResponse();
        ResponseEntity<Response> expectedResponse = new ResponseEntity<>(
                new Response(HttpStatus.OK, COURSE_CREATED.getValue(), courseResponse),
                HttpStatus.OK);

        when(courseService.createCourse(courseRequest)).thenReturn(courseResponse);

        ResponseEntity<Response> result = courseController.createCourse(courseRequest);

        verify(courseService, times(1)).createCourse(courseRequest);
        assertEquals(expectedResponse, result);
    }

    @Test
    void testGetCourses() {
        List<CourseResponse> courseResponses = new ArrayList<>();
        when(courseService.getCourses()).thenReturn(courseResponses);

        List<CourseResponse> result = courseController.getCourses();

        verify(courseService, times(1)).getCourses();
        assertEquals(courseResponses, result);
    }

    @Test
    void testGetCourseById() {
        Integer courseId = 1;
        CourseResponse courseResponse = new CourseResponse();

        when(courseService.getCourseById(courseId)).thenReturn(courseResponse);

        CourseResponse result = courseController.getCourseById(courseId);

        verify(courseService, times(1)).getCourseById(courseId);
        assertEquals(courseResponse, result);
    }

    @Test
    void testUpdateCourseById() {
        Integer courseId = 1;
        CourseRequest courseRequest = new CourseRequest();
        CourseResponse courseResponse = new CourseResponse();
        ResponseEntity<Response> expectedResponse = new ResponseEntity<>(
                new Response(HttpStatus.OK, COURSE_UPDATED.getValue(), courseResponse),
                HttpStatus.OK);

        when(courseService.updateCourseById(courseId, courseRequest)).thenReturn(courseResponse);

        ResponseEntity<Response> result = courseController.updateCourseById(courseId, courseRequest);

        verify(courseService, times(1)).updateCourseById(courseId, courseRequest);
        assertEquals(expectedResponse, result);
    }

    @Test
    void testDeleteCourseById() {
        Integer courseId = 1;
        ResponseEntity<HttpStatus> expectedResponse = new ResponseEntity<>(HttpStatus.NO_CONTENT);

        doNothing().when(courseService).deleteCourseById(courseId);

        ResponseEntity<HttpStatus> result = courseController.deleteCourseById(courseId);

        verify(courseService, times(1)).deleteCourseById(courseId);
        assertEquals(expectedResponse, result);
    }

}