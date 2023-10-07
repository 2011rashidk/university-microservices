package com.university.teacherservice.service;

import com.university.teacherservice.client.UserServiceClient;
import com.university.teacherservice.request.TeacherRequest;
import com.university.teacherservice.response.Response;
import com.university.teacherservice.response.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.university.teacherservice.enums.Constants.*;

@Service
@Slf4j
public class TeacherService {

    @Autowired
    UserServiceClient userServiceClient;

    public ResponseEntity<Response> createTeacher(TeacherRequest teacherRequest) {
        ResponseEntity<Response> response = userServiceClient.createTeacher(teacherRequest, TEACHER.getValue());
        log.info(RESPONSE.getValue(), response.getBody());
        return response;
    }

    public List<UserInfo> getTeachers(Integer pageNo, Integer pageSize) {
        List<UserInfo> teachers = userServiceClient.getTeachers(TEACHER.getValue(), pageNo, pageSize);
        log.info(RESPONSE.getValue(), teachers);
        return teachers;
    }

    public UserInfo getTeacherById(Integer userId) {
        UserInfo userInfo = userServiceClient.getTeacherById(userId);
        log.info(RESPONSE.getValue(), userInfo);
        return userInfo;
    }

    public ResponseEntity<Response> updateTeacherById(Integer userId, TeacherRequest teacherRequest) {
        ResponseEntity<Response> response = userServiceClient.updateTeacherById(userId, teacherRequest, TEACHER.getValue());
        log.info(RESPONSE.getValue(), response.getBody());
        return response;
    }

    public ResponseEntity<HttpStatus> deleteTeacherById(Integer userId) {
        ResponseEntity<HttpStatus> response = userServiceClient.deleteTeacherById(userId);
        log.info(RESPONSE.getValue(), response.getStatusCode());
        return response;
    }

}
