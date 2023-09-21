package com.university.studentservice.controller;

import com.university.studentservice.model.StudentProfile;
import com.university.studentservice.request.StudentProfileRequest;
import com.university.studentservice.response.Response;
import com.university.studentservice.service.StudentProfileService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.university.studentservice.enums.Constants.*;

@RestController
@RequestMapping("api/university/student/profile")
@Slf4j
public class StudentProfileController {

    @Autowired
    StudentProfileService studentProfileService;

    @PostMapping
    public ResponseEntity<Response> createProfile(@Valid @RequestBody StudentProfileRequest studentProfileRequest) {
        log.info(STUDENT_PROFILE_REQUEST.getValue(), studentProfileRequest);
        StudentProfile studentProfile = studentProfileService.createProfile(studentProfileRequest);
        return new ResponseEntity<>(Response.builder()
                .httpStatus(HttpStatus.CREATED)
                .message(STUDENT_PROFILE_CREATED.getValue())
                .data(studentProfile).build(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response> getProfiles() {
        List<StudentProfile> studentProfile = studentProfileService.getProfiles();
        return new ResponseEntity<>(Response.builder()
                .httpStatus(HttpStatus.OK)
                .message(STUDENT_PROFILE_RETRIEVED.getValue())
                .data(studentProfile).build(), HttpStatus.OK);
    }

    @GetMapping("{studentId}")
    public ResponseEntity<Response> getProfile(@PathVariable String studentId) {
        log.info(STUDENT_ID.getValue(), studentId);
        StudentProfile studentProfile = studentProfileService.getProfile(studentId);
        return new ResponseEntity<>(Response.builder()
                .httpStatus(HttpStatus.OK)
                .message(STUDENT_PROFILE_RETRIEVED.getValue())
                .data(studentProfile).build(), HttpStatus.OK);
    }

    @PutMapping("{studentId}")
    public ResponseEntity<Response> updateProfile(@PathVariable String studentId,
                                                  @Valid @RequestBody StudentProfileRequest studentProfileRequest) {
        log.info(STUDENT_ID.getValue(), studentId);
        log.info(STUDENT_PROFILE_REQUEST.getValue(), studentProfileRequest);
        StudentProfile studentProfile = studentProfileService.updateProfile(studentId, studentProfileRequest);
        return new ResponseEntity<>(Response.builder()
                .httpStatus(HttpStatus.OK)
                .message(STUDENT_PROFILE_UPDATED.getValue())
                .data(studentProfile).build(), HttpStatus.OK);
    }

    @DeleteMapping("{studentId}")
    public ResponseEntity<Response> deleteProfile(@PathVariable String studentId) {
        log.info(STUDENT_ID.getValue(), studentId);
        studentProfileService.deleteProfile(studentId);
        return new ResponseEntity<>(Response.builder()
                .httpStatus(HttpStatus.NO_CONTENT)
                .message(STUDENT_PROFILE_DELETED.getValue()).build(), HttpStatus.OK);
    }

    @PatchMapping("activate/{studentId}")
    public ResponseEntity<Response> activateStudent(@PathVariable String studentId) {
        log.info(STUDENT_ID.getValue(), studentId);
        studentProfileService.activateStudent(studentId);
        return new ResponseEntity<>(Response.builder()
                .httpStatus(HttpStatus.OK)
                .message(STUDENT_ACTIVATED.getValue()).build(), HttpStatus.OK);
    }

}
