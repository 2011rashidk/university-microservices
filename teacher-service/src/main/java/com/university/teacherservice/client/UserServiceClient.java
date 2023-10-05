package com.university.teacherservice.client;

import com.university.teacherservice.request.TeacherRequest;
import com.university.teacherservice.response.Response;
import com.university.teacherservice.response.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service", url = "${user.service.url}")
public interface UserServiceClient {

    @PostMapping("register")
    ResponseEntity<Response> createTeacher(@RequestBody TeacherRequest teacherRequest, @RequestParam String userType);

    @GetMapping("{userId}")
    UserInfo getTeacherById(@PathVariable Integer userId);

    @PutMapping("{userId}")
    ResponseEntity<Response> updateTeacher(@PathVariable Integer userId,
                                           @RequestBody TeacherRequest teacherRequest,
                                           @RequestParam String userType);

    @DeleteMapping("{userId}")
    ResponseEntity<HttpStatus> deleteTeacher(@PathVariable Integer userId);

}
