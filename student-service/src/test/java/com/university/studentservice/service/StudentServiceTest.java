package com.university.studentservice.service;

import com.university.studentservice.client.UserServiceClient;
import com.university.studentservice.request.StudentRequest;
import com.university.studentservice.response.Response;
import com.university.studentservice.response.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.university.studentservice.enums.Constants.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    @Mock
    private UserServiceClient userServiceClient;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateStudent() {
        StudentRequest studentRequest = new StudentRequest();
        ResponseEntity<Response> response = new ResponseEntity<>(new Response(HttpStatus.CREATED, "User created successfully", null), HttpStatus.CREATED);
        when(userServiceClient.createStudent(studentRequest, STUDENT.getValue())).thenReturn(response);

        ResponseEntity<Response> result = studentService.createStudent(studentRequest);

        verify(userServiceClient, times(1)).createStudent(studentRequest, STUDENT.getValue());
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void testGetStudents() {
        Integer pageNo = 1;
        Integer pageSize = 10;
        List<UserInfo> students = List.of(new UserInfo());
        when(userServiceClient.getStudents(STUDENT.getValue(), pageNo, pageSize)).thenReturn(students);

        List<UserInfo> result = studentService.getStudents(pageNo, pageSize);

        verify(userServiceClient, times(1)).getStudents(STUDENT.getValue(), pageNo, pageSize);
        assertNotNull(result);
        assertEquals(students.size(), result.size());
    }

    @Test
    void testGetStudentById() {
        Integer userId = 1;
        UserInfo userInfo = new UserInfo();
        when(userServiceClient.getStudentById(userId)).thenReturn(userInfo);

        UserInfo result = studentService.getStudentById(userId);

        verify(userServiceClient, times(1)).getStudentById(userId);
        assertNotNull(result);
    }

    @Test
    void testUpdateStudentById() {
        Integer userId = 1;
        StudentRequest studentRequest = new StudentRequest();
        ResponseEntity<Response> response = new ResponseEntity<>(new Response(HttpStatus.OK, "User updated successfully", null), HttpStatus.OK);
        when(userServiceClient.updateStudentById(userId, studentRequest, STUDENT.getValue())).thenReturn(response);

        ResponseEntity<Response> result = studentService.updateStudentById(userId, studentRequest);

        verify(userServiceClient, times(1)).updateStudentById(userId, studentRequest, STUDENT.getValue());
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testDeleteStudentById() {
        Integer userId = 1;
        ResponseEntity<HttpStatus> response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        when(userServiceClient.deleteStudentById(userId)).thenReturn(response);

        ResponseEntity<HttpStatus> result = studentService.deleteStudentById(userId);

        verify(userServiceClient, times(1)).deleteStudentById(userId);
        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }
}