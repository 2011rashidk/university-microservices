package com.university.courseservice.controller;

import com.university.courseservice.request.CourseRequest;
import com.university.courseservice.response.CourseResponse;
import com.university.courseservice.response.Response;
import com.university.courseservice.service.CourseService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.university.courseservice.enums.Constants.*;

@RestController
@RequestMapping("api/university/course")
@Slf4j
public class CourseController {

    @Autowired
    CourseService courseService;

    @PostMapping
    public ResponseEntity<Response> createCourse(@Valid @RequestBody CourseRequest courseRequest) {
        log.info(COURSE_REQUEST.getValue(), courseRequest);
        CourseResponse courseResponse = courseService.createCourse(courseRequest);
        return new ResponseEntity<>(
                new Response(HttpStatus.OK, COURSE_CREATED.getValue(), courseResponse),
                HttpStatus.OK);
    }

    @GetMapping
    public List<CourseResponse> getCourses() {
        return courseService.getCourses();
    }

    @GetMapping("{courseId}")
    public CourseResponse getCourseById(@PathVariable Integer courseId) {
        log.info(COURSE_ID.getValue(), courseId);
        return courseService.getCourseById(courseId);
    }

    @PutMapping("{courseId}")
    public ResponseEntity<Response> updateCourse(@PathVariable Integer courseId,
                                                 @Valid @RequestBody CourseRequest courseRequest) {
        log.info(COURSE_ID.getValue(), courseId);
        log.info(COURSE_REQUEST.getValue(), courseRequest);
        CourseResponse courseResponse = courseService.updateCourse(courseId, courseRequest);
        return new ResponseEntity<>(
                new Response(HttpStatus.OK, COURSE_UPDATED.getValue(), courseResponse),
                HttpStatus.OK);
    }

    @DeleteMapping("{courseId}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable Integer courseId) {
        log.info(COURSE_ID.getValue(), courseId);
        courseService.deleteCourse(courseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
