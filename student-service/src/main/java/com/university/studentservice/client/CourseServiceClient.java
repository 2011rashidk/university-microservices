package com.university.studentservice.client;

import com.university.studentservice.response.CourseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "course-service", url = "${course.service.url}")
public interface CourseServiceClient {

    @GetMapping
    List<CourseResponse> getCourses();
}
