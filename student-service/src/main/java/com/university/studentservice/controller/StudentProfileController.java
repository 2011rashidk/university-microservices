package com.university.studentservice.controller;

import com.university.studentservice.model.StudentProfile;
import com.university.studentservice.request.CourseRequest;
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
import java.util.Map;
import java.util.Set;

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
    public ResponseEntity<Response> getProfiles(@RequestParam(required = false) Integer pageNo,
                                                @RequestParam(required = false) Integer pageSize) {
        List<StudentProfile> studentProfile = studentProfileService.getProfiles(pageNo, pageSize);
        return new ResponseEntity<>(Response.builder()
                .httpStatus(HttpStatus.OK)
                .message(STUDENT_PROFILE_RETRIEVED.getValue())
                .data(studentProfile).build(), HttpStatus.OK);
    }

    @GetMapping("{studentId}")
    public StudentProfile getProfile(@PathVariable String studentId) {
        log.info(STUDENT_ID.getValue(), studentId);
        return studentProfileService.getProfile(studentId);
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

    @PostMapping("enroll-course/{studentId}")
    public ResponseEntity<Response> enrollCourse(@PathVariable String studentId,
                                                 @Valid @RequestBody CourseRequest courseRequest) {
        Map<String, Set<String>> assignedCourse = studentProfileService.enrollCourse(studentId, courseRequest);
        return new ResponseEntity<>(
                new Response(HttpStatus.OK, COURSES_ENROLLED.getValue(), assignedCourse), HttpStatus.OK);
    }

    @PostMapping("remove-course/{studentId}")
    public ResponseEntity<Response> removeCourse(@PathVariable String studentId,
                                                 @Valid @RequestBody CourseRequest courseRequest) {
        Map<String, Set<String>> removedCourse = studentProfileService.removeCourse(studentId, courseRequest);
        return new ResponseEntity<>(
                new Response(HttpStatus.OK, COURSE_REMOVED.getValue(), removedCourse), HttpStatus.OK);
    }

}
