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

    public UserInfo getTeacherById(Integer userId) {
        UserInfo userInfo = userServiceClient.getTeacherById(userId);
        log.info(RESPONSE.getValue(), userInfo);
        return userInfo;
    }

    public ResponseEntity<Response> updateTeacher(Integer userId, TeacherRequest teacherRequest) {
        ResponseEntity<Response> response = userServiceClient.updateTeacher(userId, teacherRequest, TEACHER.getValue());
        log.info(RESPONSE.getValue(), response.getBody());
        return response;
    }

    public ResponseEntity<HttpStatus> deleteTeacher(Integer userId) {
        ResponseEntity<HttpStatus> response = userServiceClient.deleteTeacher(userId);
        log.info(RESPONSE.getValue(), response.getStatusCode());
        return response;
    }

}
