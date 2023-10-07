package com.university.studentservice.client;

import com.university.studentservice.request.StudentRequest;
import com.university.studentservice.response.Response;
import com.university.studentservice.response.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-service", url = "${user.service.url}")
public interface UserServiceClient {

    @PostMapping("register")
    ResponseEntity<Response> createStudent(@RequestBody StudentRequest studentRequest, @RequestParam String userType);

    @GetMapping
    List<UserInfo> getStudents(@RequestParam(required = false) String userType,
                               @RequestParam(required = false) Integer pageNo,
                               @RequestParam(required = false) Integer pageSize);
    @GetMapping("{userId}")
    UserInfo getStudentById(@PathVariable Integer userId);

    @PutMapping("{userId}")
    ResponseEntity<Response> updateStudentById(@PathVariable Integer userId,
                                               @RequestBody StudentRequest studentRequest,
                                               @RequestParam String userType);

    @DeleteMapping("{userId}")
    ResponseEntity<HttpStatus> deleteStudentById(@PathVariable Integer userId);

}
