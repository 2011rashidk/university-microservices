package com.university.teacherservice.service;

import com.university.teacherservice.client.UserServiceClient;
import com.university.teacherservice.request.TeacherRequest;
import com.university.teacherservice.response.Response;
import com.university.teacherservice.response.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static com.university.teacherservice.enums.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeacherServiceTest {

    @Mock
    private UserServiceClient userServiceClient;

    @InjectMocks
    private TeacherService teacherService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTeacher() {
        TeacherRequest teacherRequest = new TeacherRequest();
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(new Response(HttpStatus.OK,"User created successfully",  null), HttpStatus.CREATED);
        when(userServiceClient.createTeacher(teacherRequest, TEACHER.getValue())).thenReturn(responseEntity);

        ResponseEntity<Response> result = teacherService.createTeacher(teacherRequest);

        verify(userServiceClient, times(1)).createTeacher(teacherRequest, TEACHER.getValue());
        assertNotNull(result);
    }

    @Test
    void testGetTeachers() {
        Integer pageNo = 1;
        Integer pageSize = 10;
        List<UserInfo> teachers = Collections.singletonList(new UserInfo());
        when(userServiceClient.getTeachers(TEACHER.getValue(), pageNo, pageSize)).thenReturn(teachers);

        List<UserInfo> result = teacherService.getTeachers(pageNo, pageSize);

        verify(userServiceClient, times(1)).getTeachers(TEACHER.getValue(), pageNo, pageSize);
        assertNotNull(result);
    }

    @Test
    void testGetTeacherById() {
        Integer userId = 1;
        UserInfo userInfo = new UserInfo();
        when(userServiceClient.getTeacherById(userId)).thenReturn(userInfo);

        UserInfo result = teacherService.getTeacherById(userId);

        verify(userServiceClient, times(1)).getTeacherById(userId);
        assertNotNull(result);
    }

    @Test
    void testUpdateTeacherById() {
        Integer userId = 1;
        TeacherRequest teacherRequest = new TeacherRequest();
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(new Response(HttpStatus.OK, "User updated successfully", null), HttpStatus.OK);
        when(userServiceClient.updateTeacherById(userId, teacherRequest, TEACHER.getValue())).thenReturn(responseEntity);

        ResponseEntity<Response> result = teacherService.updateTeacherById(userId, teacherRequest);

        verify(userServiceClient, times(1)).updateTeacherById(userId, teacherRequest, TEACHER.getValue());
        assertNotNull(result);
    }

    @Test
    void testDeleteTeacherById() {
        Integer userId = 1;
        ResponseEntity<HttpStatus> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(userServiceClient.deleteTeacherById(userId)).thenReturn(responseEntity);

        ResponseEntity<HttpStatus> result = teacherService.deleteTeacherById(userId);

        verify(userServiceClient, times(1)).deleteTeacherById(userId);
        assertNotNull(result);
    }

}