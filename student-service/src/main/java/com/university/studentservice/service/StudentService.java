package com.university.studentservice.service;

import com.university.studentservice.client.UserServiceClient;
import com.university.studentservice.request.StudentRequest;
import com.university.studentservice.response.Response;
import com.university.studentservice.response.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.university.studentservice.enums.Constants.*;

@Service
@Slf4j
public class StudentService {

    @Autowired
    UserServiceClient userServiceClient;

    public ResponseEntity<Response> createStudent(StudentRequest studentRequest) {
        ResponseEntity<Response> response = userServiceClient.createStudent(studentRequest, STUDENT.getValue());
        log.info(RESPONSE.getValue(), response.getBody());
        return response;
    }

    public List<UserInfo> getStudents(Integer pageNo, Integer pageSize) {
        List<UserInfo> teachers = userServiceClient.getStudents(STUDENT.getValue(), pageNo, pageSize);
        log.info(RESPONSE.getValue(), teachers);
        return teachers;
    }

    public UserInfo getStudentById(Integer userId) {
        UserInfo userInfo = userServiceClient.getStudentById(userId);
        log.info(RESPONSE.getValue(), userInfo);
        return userInfo;
    }

    public ResponseEntity<Response> updateStudentById(Integer userId, StudentRequest studentRequest) {
        ResponseEntity<Response> response = userServiceClient.updateStudentById(userId, studentRequest, STUDENT.getValue());
        log.info(RESPONSE.getValue(), response.getBody());
        return response;
    }

    public ResponseEntity<HttpStatus> deleteStudentById(Integer userId) {
        ResponseEntity<HttpStatus> response = userServiceClient.deleteStudentById(userId);
        log.info(RESPONSE.getValue(), response.getStatusCode());
        return response;
    }

}
