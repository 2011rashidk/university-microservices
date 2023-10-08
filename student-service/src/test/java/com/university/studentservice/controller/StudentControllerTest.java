package com.university.studentservice.controller;

import com.university.studentservice.request.StudentRequest;
import com.university.studentservice.response.Response;
import com.university.studentservice.response.UserInfo;
import com.university.studentservice.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateStudent() {
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setFirstname("Rahul");
        studentRequest.setLastname("K");
        studentRequest.setAge(27);
        studentRequest.setEmail("rahul@hu.com");
        studentRequest.setPassword("Test123");
        studentRequest.setMobile("9000000000");
        studentRequest.setAddress("Bangalore, Karnataka");

        Response response = new Response(HttpStatus.CREATED, "Users created successfully", null);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, HttpStatus.CREATED);
        when(studentService.createStudent(studentRequest)).thenReturn(responseEntity);
        ResponseEntity<Response> result = studentController.createStudent(studentRequest);
        verify(studentService).createStudent(studentRequest);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void testGetStudents() {
        List<UserInfo> userInfos = Collections.singletonList(new UserInfo());
        when(studentService.getStudents(null, null)).thenReturn(userInfos);
        List<UserInfo> result = studentController.getStudents(null, null);
        verify(studentService).getStudents(null, null);
        assertEquals(userInfos, result);
    }

    @Test
    void testGetStudentById() {
        Integer userId = 1;
        UserInfo userInfo = new UserInfo(/* Initialize with data */);
        when(studentService.getStudentById(userId)).thenReturn(userInfo);
        UserInfo result = studentController.getStudentById(userId);
        verify(studentService).getStudentById(userId);
        assertEquals(userInfo, result);
    }

    @Test
    void testUpdateStudentById() {
        Integer userId = 1;
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setFirstname("Rahul");
        studentRequest.setLastname("K");
        studentRequest.setAge(27);
        studentRequest.setEmail("rahul@hu.com");
        studentRequest.setPassword("Test123");
        studentRequest.setMobile("9000000000");
        studentRequest.setAddress("Bangalore, Karnataka");
        Response response = new Response(HttpStatus.CREATED, "Users updated successfully", null);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
        when(studentService.updateStudentById(userId, studentRequest)).thenReturn(responseEntity);
        ResponseEntity<Response> result = studentController.updateStudentById(userId, studentRequest);
        verify(studentService).updateStudentById(userId, studentRequest);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testDeleteStudentById() {
        Integer userId = 1;
        ResponseEntity<HttpStatus> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        when(studentService.deleteStudentById(userId)).thenReturn(responseEntity);
        ResponseEntity<HttpStatus> result = studentController.deleteStudentById(userId);
        verify(studentService).deleteStudentById(userId);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }
}