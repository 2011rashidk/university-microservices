package com.university.courseservice.service;

import com.university.courseservice.entity.Course;
import com.university.courseservice.exception.NotFoundException;
import com.university.courseservice.repository.CourseRepository;
import com.university.courseservice.request.CourseRequest;
import com.university.courseservice.response.CourseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCourse() {
        CourseRequest courseRequest = new CourseRequest();
        courseRequest.setCourseName("Advanced Java");
        courseRequest.setDescription("Advanced Java course");

        Course savedCourse = new Course();
        savedCourse.setCourseId(1);

        when(courseRepository.save(any(Course.class))).thenReturn(savedCourse);

        CourseResponse result = courseService.createCourse(courseRequest);

        verify(courseRepository, times(1)).save(any(Course.class));
        assertEquals(savedCourse.getCourseId(), result.getCourseId());
    }

    @Test
    void testGetCourses() {
        List<Course> courseList = new ArrayList<>();
        List<CourseResponse> expectedResponseList = new ArrayList<>();
        when(courseRepository.findAll()).thenReturn(courseList);

        List<CourseResponse> result = courseService.getCourses();

        verify(courseRepository, times(1)).findAll();
        assertEquals(expectedResponseList, result);
    }

    @Test
    void testGetCourseById() {
        Integer courseId = 1;
        Course course = new Course();
        CourseResponse expectedResponse = new CourseResponse();
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        CourseResponse result = courseService.getCourseById(courseId);

        verify(courseRepository, times(1)).findById(courseId);
        assertEquals(expectedResponse.getCourseId(), result.getCourseId());
    }

    @Test
    void testUpdateCourseById() {
        Integer courseId = 1;
        CourseRequest courseRequest = new CourseRequest();
        courseRequest.setCourseName("Python");
        courseRequest.setDescription("Python course");

        Course existingCourse = new Course();
        existingCourse.setCourseId(courseId);
        existingCourse.setCourseName("Python");
        existingCourse.setDescription("Course for Python");

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(Mockito.any(Course.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CourseResponse result = courseService.updateCourseById(courseId, courseRequest);

        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, times(1)).save(any(Course.class));

        assertEquals(courseId, result.getCourseId());
        assertEquals("Python course", result.getDescription());
    }

    @Test
    void testDeleteCourseById() {
        Integer courseId = 1;
        Course course = new Course();
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        courseService.deleteCourseById(courseId);

        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, times(1)).save(course);
        assertFalse(course.isActive());
    }

    @Test
    void testDeleteCourseByIdNotFound() {
        Integer courseId = 1;
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> courseService.deleteCourseById(courseId));

        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, never()).save(any());
    }

}