package com.university.attendanceservice.client;

import com.university.attendanceservice.response.StudentProfile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "student-service", url = "${student.service.url}")
public interface StudentServiceClient {

    @GetMapping("{studentId}")
    StudentProfile getProfile(@PathVariable String studentId);

}
