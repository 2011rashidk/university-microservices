package com.university.gradeservice.service;

import com.university.gradeservice.client.CourseServiceClient;
import com.university.gradeservice.client.StudentServiceClient;
import com.university.gradeservice.entity.Grade;
import com.university.gradeservice.entity.StudentGrade;
import com.university.gradeservice.repository.StudentGradeRepository;
import com.university.gradeservice.request.StudentGradeRequest;
import com.university.gradeservice.response.CourseResponse;
import com.university.gradeservice.response.StudentCourseGrade;
import com.university.gradeservice.response.StudentGradeResponse;
import com.university.gradeservice.response.StudentProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentGradeServiceTest {

    @Mock
    private StudentGradeRepository studentGradeRepository;

    @Mock
    private GradeService gradeService;

    @Mock
    private StudentServiceClient studentServiceClient;

    @Mock
    private CourseServiceClient courseServiceClient;

    @InjectMocks
    private StudentGradeService studentGradeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAssignStudentGrade() {
        StudentGradeRequest studentGradeRequest = new StudentGradeRequest();
        studentGradeRequest.setCourseId(1);
        StudentProfile studentProfile = new StudentProfile();
        studentProfile.setCoursesEnrolled(Set.of("Core Java"));
        CourseResponse course = new CourseResponse();
        course.setCourseName("Core Java");
        Grade grade = new Grade();
        StudentGrade savedStudentGrade = new StudentGrade();
        StudentGradeResponse expectedResponse = new StudentGradeResponse();

        when(studentServiceClient.getProfile(studentGradeRequest.getStudentId())).thenReturn(studentProfile);
        when(courseServiceClient.getCourseById(studentGradeRequest.getCourseId())).thenReturn(course);
        when(studentGradeRepository.save(any(StudentGrade.class))).thenReturn(savedStudentGrade);
        when(gradeService.getGradeByMarks(studentGradeRequest.getMarks())).thenReturn(grade);

        StudentGradeResponse result = studentGradeService.assignStudentGrade(studentGradeRequest);

        verify(studentServiceClient, times(1)).getProfile(studentGradeRequest.getStudentId());
        verify(courseServiceClient, times(1)).getCourseById(studentGradeRequest.getCourseId());
        verify(studentGradeRepository, times(1)).save(any(StudentGrade.class));
        verify(gradeService, times(1)).getGradeByMarks(studentGradeRequest.getMarks());
        assertNotNull(result);
        assertEquals(expectedResponse, result);
    }

    @Test
    void testGetStudentGrade() {
        Integer studentGradeId = 1;
        StudentGrade studentGrade = new StudentGrade();
        studentGrade.setGrade(new Grade());

        when(studentGradeRepository.findById(studentGradeId)).thenReturn(Optional.of(studentGrade));

        StudentGradeResponse result = studentGradeService.getStudentGrade(studentGradeId);

        verify(studentGradeRepository, times(1)).findById(studentGradeId);
        assertNotNull(result);
    }

    @Test
    void testGetGradeByCourse() {
        Integer courseId = 1;
        CourseResponse course = new CourseResponse();
        List<StudentGrade> studentGradeList = new ArrayList<>();

        when(courseServiceClient.getCourseById(courseId)).thenReturn(course);
        when(studentGradeRepository.findByCourseId(courseId)).thenReturn(studentGradeList);

        StudentCourseGrade result = studentGradeService.getGradeByCourse(courseId);

        verify(courseServiceClient, times(1)).getCourseById(courseId);
        verify(studentGradeRepository, times(1)).findByCourseId(courseId);
        assertNotNull(result);
    }

}