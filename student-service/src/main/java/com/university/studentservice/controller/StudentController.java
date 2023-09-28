package com.university.studentservice.controller;

import com.university.studentservice.request.StudentRequest;
import com.university.studentservice.response.Response;
import com.university.studentservice.response.UserInfo;
import com.university.studentservice.service.StudentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.university.studentservice.enums.Constants.*;


@RestController
@RequestMapping("api/university/student")
@Slf4j
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping
    public ResponseEntity<Response> createStudent(@Valid @RequestBody StudentRequest studentRequest) {
        log.info(STUDENT_REQUEST.getValue(), studentRequest);
        return studentService.createStudent(studentRequest);
    }

    @GetMapping("{userId}")
    public UserInfo getStudentById(@PathVariable Integer userId) {
        log.info(USER_ID.getValue(), userId);
        return studentService.getStudentById(userId);
    }

    @PutMapping("{userId}")
    public ResponseEntity<Response> updateStudent(@PathVariable Integer userId,
                                                  @Valid @RequestBody StudentRequest studentRequest) {
        log.info(USER_ID.getValue(), userId);
        log.info(STUDENT_REQUEST.getValue(), studentRequest);
        return studentService.updateStudent(userId, studentRequest);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable Integer userId) {
        log.info(USER_ID.getValue(), userId);
        return studentService.deleteStudent(userId);
    }

}
