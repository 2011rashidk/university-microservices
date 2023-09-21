package com.university.studentservice.service;

import com.university.studentservice.client.UserServiceClient;
import com.university.studentservice.request.StudentRequest;
import com.university.studentservice.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public ResponseEntity<Response> getStudentById(Integer userId) {
        ResponseEntity<Response> response = userServiceClient.getStudentById(userId);
        log.info(RESPONSE.getValue(), response.getBody());
        return response;
    }

    public ResponseEntity<Response> updateStudent(Integer userId, StudentRequest studentRequest) {
        ResponseEntity<Response> response = userServiceClient.updateStudent(userId, studentRequest, STUDENT.getValue());
        log.info(RESPONSE.getValue(), response.getBody());
        return response;
    }

    public ResponseEntity<HttpStatus> deleteStudent(Integer userId) {
        ResponseEntity<HttpStatus> response = userServiceClient.deleteStudent(userId);
        log.info(RESPONSE.getValue(), response.getStatusCode());
        return response;
    }

}
