package com.university.teacherservice.controller;

import com.university.teacherservice.request.TeacherRequest;
import com.university.teacherservice.response.Response;
import com.university.teacherservice.response.UserInfo;
import com.university.teacherservice.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TeacherControllerTest {

    @Mock
    private TeacherService teacherService;

    @InjectMocks
    private TeacherController teacherController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTeacher() {
        TeacherRequest teacherRequest = new TeacherRequest();
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(new Response(HttpStatus.OK, "Teacher Created", null), HttpStatus.OK);
        when(teacherService.createTeacher(teacherRequest)).thenReturn(responseEntity);

        ResponseEntity<Response> result = teacherController.createTeacher(teacherRequest);

        verify(teacherService, times(1)).createTeacher(teacherRequest);
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testGetTeachers() {
        Integer pageNo = 1;
        Integer pageSize = 10;
        List<UserInfo> teachers = Collections.singletonList(new UserInfo());
        when(teacherService.getTeachers(pageNo, pageSize)).thenReturn(teachers);

        List<UserInfo> result = teacherController.getTeachers(pageNo, pageSize);

        verify(teacherService, times(1)).getTeachers(pageNo, pageSize);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testGetTeacherById() {
        Integer userId = 1;
        UserInfo teacherInfo = new UserInfo();
        when(teacherService.getTeacherById(userId)).thenReturn(teacherInfo);

        UserInfo result = teacherController.getTeacherById(userId);

        verify(teacherService, times(1)).getTeacherById(userId);
        assertNotNull(result);
    }

    @Test
    void testUpdateTeacherById() {
        Integer userId = 1;
        TeacherRequest teacherRequest = new TeacherRequest();
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(new Response(HttpStatus.OK, "Teacher Updated", null), HttpStatus.OK);
        when(teacherService.updateTeacherById(userId, teacherRequest)).thenReturn(responseEntity);

        ResponseEntity<Response> result = teacherController.updateTeacherById(userId, teacherRequest);

        verify(teacherService, times(1)).updateTeacherById(userId, teacherRequest);
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testDeleteTeacherById() {
        Integer userId = 1;
        ResponseEntity<HttpStatus> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        when(teacherService.deleteTeacherById(userId)).thenReturn(responseEntity);

        ResponseEntity<HttpStatus> result = teacherController.deleteTeacherById(userId);

        verify(teacherService, times(1)).deleteTeacherById(userId);
        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

}