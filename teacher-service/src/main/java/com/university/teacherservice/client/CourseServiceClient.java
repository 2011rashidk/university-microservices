package com.university.teacherservice.client;

import com.university.teacherservice.response.CourseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "course-service", url = "${course.service.url}")
public interface CourseServiceClient {

    @GetMapping
    List<CourseResponse> getCourses();
}
