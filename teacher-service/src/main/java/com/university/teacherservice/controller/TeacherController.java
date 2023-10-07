package com.university.teacherservice.controller;

import com.university.teacherservice.request.TeacherRequest;
import com.university.teacherservice.response.Response;
import com.university.teacherservice.response.UserInfo;
import com.university.teacherservice.service.TeacherService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.university.teacherservice.enums.Constants.*;


@RestController
@RequestMapping("api/university/teacher")
@Slf4j
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @PostMapping("register")
    public ResponseEntity<Response> createTeacher(@Valid @RequestBody TeacherRequest teacherRequest) {
        log.info(TEACHER_REQUEST.getValue(), teacherRequest);
        return teacherService.createTeacher(teacherRequest);
    }

    @GetMapping
    public List<UserInfo> getTeachers(@RequestParam(required = false) Integer pageNo,
                                      @RequestParam(required = false) Integer pageSize) {
        return teacherService.getTeachers(pageNo, pageSize);
    }

    @GetMapping("{userId}")
    public UserInfo getTeacherById(@PathVariable Integer userId) {
        log.info(USER_ID.getValue(), userId);
        return teacherService.getTeacherById(userId);
    }

    @PutMapping("{userId}")
    public ResponseEntity<Response> updateTeacherById(@PathVariable Integer userId,
                                                      @Valid @RequestBody TeacherRequest teacherRequest) {
        log.info(USER_ID.getValue(), userId);
        log.info(TEACHER_REQUEST.getValue(), teacherRequest);
        return teacherService.updateTeacherById(userId, teacherRequest);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<HttpStatus> deleteTeacherById(@PathVariable Integer userId) {
        log.info(USER_ID.getValue(), userId);
        return teacherService.deleteTeacherById(userId);
    }

}
