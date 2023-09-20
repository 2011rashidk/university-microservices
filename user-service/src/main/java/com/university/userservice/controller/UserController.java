package com.university.userservice.controller;

import com.university.userservice.request.UserRequest;
import com.university.userservice.response.Response;
import com.university.userservice.response.UserResponse;
import com.university.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/university/user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<Response> createUser(@Valid @RequestBody UserRequest userRequest,
                                               @RequestParam String userType) {
        log.info("userRequest: {}", userRequest);
        log.info("userType: {}", userType);
        UserResponse userResponse = userService.createUser(userRequest, userType);
        return new ResponseEntity<>(
                Response.builder()
                        .httpStatus(HttpStatus.CREATED)
                        .message("User created successfully")
                        .data(userResponse)
                        .build(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response> getUsers() {
        List<UserResponse> userResponseList = userService.getUsers();
        return new ResponseEntity<>(
                Response.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("Users fetched successfully")
                        .data(userResponseList)
                        .build(), HttpStatus.OK);
    }

    @GetMapping("{userId}")
    public ResponseEntity<Response> getUserById(@PathVariable Integer userId) {
        log.info("userId: {}", userId);
        UserResponse userResponse = userService.getUserById(userId);
        return new ResponseEntity<>(
                Response.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("User fetched successfully")
                        .data(userResponse)
                        .build(), HttpStatus.OK);
    }

    @PutMapping("{userId}")
    public ResponseEntity<Response> updateUser(@PathVariable Integer userId,
                                               @Valid @RequestBody UserRequest userRequest) {
        log.info("userId: {}", userId);
        log.info("userRequest: {}", userRequest);
        UserResponse userResponse = userService.updateUser(userId, userRequest);
        return new ResponseEntity<>(
                Response.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("User fetched successfully")
                        .data(userResponse)
                        .build(), HttpStatus.OK);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<HttpStatus> updateUser(@PathVariable Integer userId) {
        log.info("userId: {}", userId);
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
