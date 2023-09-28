package com.university.studentservice.client;

import com.university.studentservice.request.StudentRequest;
import com.university.studentservice.response.Response;
import com.university.studentservice.response.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service", url = "${user.service.url}")
public interface UserServiceClient {

    @PostMapping
    ResponseEntity<Response> createStudent(@RequestBody StudentRequest studentRequest, @RequestParam String userType);


    @GetMapping("{userId}")
    UserInfo getStudentById(@PathVariable Integer userId);

    @PutMapping("{userId}")
    ResponseEntity<Response> updateStudent(@PathVariable Integer userId,
                                                  @RequestBody StudentRequest studentRequest,
                                                  @RequestParam String userType);

    @DeleteMapping("{userId}")
    ResponseEntity<HttpStatus> deleteStudent(@PathVariable Integer userId);

}
