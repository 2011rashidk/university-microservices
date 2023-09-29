package com.university.gradeservice.client;

import com.university.gradeservice.response.StudentProfile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "student-service", url = "${student.service.url}")
public interface StudentServiceClient {

    @GetMapping("{studentId}")
    StudentProfile getProfile(@PathVariable String studentId);

}
