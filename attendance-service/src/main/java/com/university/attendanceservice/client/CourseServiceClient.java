package com.university.attendanceservice.client;

import com.university.attendanceservice.response.CourseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "course-service", url = "${course.service.url}")
public interface CourseServiceClient {

    @GetMapping("{courseId}")
    CourseResponse getCourseById(@PathVariable Integer courseId);
}
