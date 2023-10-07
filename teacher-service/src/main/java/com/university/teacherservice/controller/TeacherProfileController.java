package com.university.teacherservice.controller;

import com.university.teacherservice.model.TeacherProfile;
import com.university.teacherservice.request.CourseRequest;
import com.university.teacherservice.request.TeacherProfileRequest;
import com.university.teacherservice.response.Response;
import com.university.teacherservice.service.TeacherProfileService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.university.teacherservice.enums.Constants.*;

@RestController
@RequestMapping("api/university/teacher/profile")
@Slf4j
public class TeacherProfileController {

    @Autowired
    TeacherProfileService teacherProfileService;

    @PostMapping
    public ResponseEntity<Response> createProfile(@Valid @RequestBody TeacherProfileRequest teacherProfileRequest) {
        log.info(TEACHER_PROFILE_REQUEST.getValue(), teacherProfileRequest);
        TeacherProfile teacherProfile = teacherProfileService.createProfile(teacherProfileRequest);
        return new ResponseEntity<>(Response.builder()
                .httpStatus(HttpStatus.CREATED)
                .message(TEACHER_PROFILE_CREATED.getValue())
                .data(teacherProfile).build(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response> getProfiles(@RequestParam(required = false) Integer pageNo,
                                                @RequestParam(required = false) Integer pageSize) {
        List<TeacherProfile> teacherProfile = teacherProfileService.getProfiles(pageNo, pageSize);
        return new ResponseEntity<>(Response.builder()
                .httpStatus(HttpStatus.OK)
                .message(TEACHER_PROFILE_RETRIEVED.getValue())
                .data(teacherProfile).build(), HttpStatus.OK);
    }

    @GetMapping("{teacherId}")
    public ResponseEntity<Response> getProfile(@PathVariable String teacherId) {
        log.info(TEACHER_ID.getValue(), teacherId);
        TeacherProfile teacherProfile = teacherProfileService.getProfile(teacherId);
        return new ResponseEntity<>(Response.builder()
                .httpStatus(HttpStatus.OK)
                .message(TEACHER_PROFILE_RETRIEVED.getValue())
                .data(teacherProfile).build(), HttpStatus.OK);
    }

    @PutMapping("{teacherId}")
    public ResponseEntity<Response> updateProfile(@PathVariable String teacherId,
                                                  @Valid @RequestBody TeacherProfileRequest teacherProfileRequest) {
        log.info(TEACHER_ID.getValue(), teacherId);
        log.info(TEACHER_PROFILE_REQUEST.getValue(), teacherProfileRequest);
        TeacherProfile teacherProfile = teacherProfileService.updateProfile(teacherId, teacherProfileRequest);
        return new ResponseEntity<>(Response.builder()
                .httpStatus(HttpStatus.OK)
                .message(TEACHER_PROFILE_UPDATED.getValue())
                .data(teacherProfile).build(), HttpStatus.OK);
    }

    @DeleteMapping("{teacherId}")
    public ResponseEntity<Response> deleteProfile(@PathVariable String teacherId) {
        log.info(TEACHER_ID.getValue(), teacherId);
        teacherProfileService.deleteProfile(teacherId);
        return new ResponseEntity<>(Response.builder()
                .httpStatus(HttpStatus.NO_CONTENT)
                .message(TEACHER_PROFILE_DELETED.getValue()).build(), HttpStatus.OK);
    }

    @PatchMapping("activate/{teacherId}")
    public ResponseEntity<Response> activateTeacher(@PathVariable String teacherId) {
        log.info(TEACHER_ID.getValue(), teacherId);
        teacherProfileService.activateTeacher(teacherId);
        return new ResponseEntity<>(Response.builder()
                .httpStatus(HttpStatus.OK)
                .message(TEACHER_ACTIVATED.getValue()).build(), HttpStatus.OK);
    }

    @PostMapping("assign-course/{teacherId}")
    public ResponseEntity<Response> assignCourse(@PathVariable String teacherId,
                                                 @Valid @RequestBody CourseRequest courseRequest) {
        Map<String, Set<String>> assignedCourse = teacherProfileService.assignCourse(teacherId, courseRequest);
        return new ResponseEntity<>(
                new Response(HttpStatus.OK, COURSES_ASSIGNED.getValue(), assignedCourse), HttpStatus.OK);
    }

    @PostMapping("remove-course/{teacherId}")
    public ResponseEntity<Response> removeCourse(@PathVariable String teacherId,
                                                 @Valid @RequestBody CourseRequest courseRequest) {
        Map<String, Set<String>> removedCourse = teacherProfileService.removeCourse(teacherId, courseRequest);
        return new ResponseEntity<>(
                new Response(HttpStatus.OK, COURSE_REMOVED.getValue(), removedCourse), HttpStatus.OK);
    }

}
