package com.university.studentservice.controller;

import com.university.studentservice.client.CourseServiceClient;
import com.university.studentservice.response.CourseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.university.studentservice.enums.Constants.AVAILABLE_COURSES;

@RestController
@RequestMapping("api/university/student/course")
@Slf4j
public class CourseServiceController {

    @Autowired
    CourseServiceClient courseServiceClient;

    @GetMapping
    public List<CourseResponse> getAvailableCourses() {
        List<CourseResponse> availableCourses = courseServiceClient.getCourses();
        log.info(AVAILABLE_COURSES.getValue(), availableCourses);
        return availableCourses;
    }

}
