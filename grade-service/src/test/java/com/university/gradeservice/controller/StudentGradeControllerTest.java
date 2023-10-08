package com.university.gradeservice.controller;

import com.university.gradeservice.request.StudentGradeRequest;
import com.university.gradeservice.response.Response;
import com.university.gradeservice.response.StudentCourseGrade;
import com.university.gradeservice.response.StudentGradeResponse;
import com.university.gradeservice.service.StudentGradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentGradeControllerTest {

    @Mock
    private StudentGradeService studentGradeService;

    @InjectMocks
    private StudentGradeController studentGradeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAssignStudentGrade() {
        StudentGradeRequest studentGradeRequest = new StudentGradeRequest();
        studentGradeRequest.setStudentId("HS1");
        studentGradeRequest.setCourseId(1);
        studentGradeRequest.setMarks(90);
        studentGradeRequest.setComments("Very good!");

        StudentGradeResponse studentGradeResponse = new StudentGradeResponse();
        studentGradeResponse.setStudentGradeId(1);
        studentGradeResponse.setStudentId("HS1");
        studentGradeResponse.setCourseId(1);
        studentGradeResponse.setGrade("A");
        studentGradeResponse.setComments("Very good!");

        when(studentGradeService.assignStudentGrade(studentGradeRequest)).thenReturn(studentGradeResponse);

        ResponseEntity<Response> result = studentGradeController.assignStudentGrade(studentGradeRequest);

        verify(studentGradeService, times(1)).assignStudentGrade(studentGradeRequest);
        assertNotNull(result);
    }

    @Test
    void testGetStudentGrade() {
        Integer studentGradeId = 1;
        StudentGradeResponse studentGradeResponse = new StudentGradeResponse();
        studentGradeResponse.setStudentGradeId(1);
        studentGradeResponse.setStudentId("HS1");
        studentGradeResponse.setCourseId(1);
        studentGradeResponse.setGrade("A");
        studentGradeResponse.setComments("Very good!");
        when(studentGradeService.getStudentGrade(studentGradeId)).thenReturn(studentGradeResponse);

        StudentGradeResponse result = studentGradeController.getStudentGrade(studentGradeId);

        verify(studentGradeService, times(1)).getStudentGrade(studentGradeId);
        assertNotNull(result);
    }

    @Test
    void testGetGradeByCourse() {
        Integer courseId = 1;
        StudentCourseGrade studentCourseGrade = new StudentCourseGrade();
        studentCourseGrade.setCourseId(1);
        studentCourseGrade.setCourseName("Core Java");
        TreeMap<String, String> studentGrade = new TreeMap<>();
        studentGrade.put("Rahul", "A");
        studentCourseGrade.setStudentGrade(studentGrade);
        when(studentGradeService.getGradeByCourse(courseId)).thenReturn(studentCourseGrade);

        StudentCourseGrade result = studentGradeController.getGradeByCourse(courseId);

        verify(studentGradeService, times(1)).getGradeByCourse(courseId);
        assertNotNull(result);
    }

}