package com.university.userservice.controller;

import com.university.userservice.request.UserRequest;
import com.university.userservice.response.Response;
import com.university.userservice.response.UserResponse;
import com.university.userservice.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.university.userservice.enums.Constants.*;

@RestController
@RequestMapping("api/university/user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("register")
    public ResponseEntity<Response> createUser(@Valid @RequestBody UserRequest userRequest,
                                               @RequestParam String userType) {
        log.info(USER_REQUEST.getValue(), userRequest);
        log.info(USER_TYPE.getValue(), userType);
        UserResponse userResponse = userService.createUser(userRequest, userType);
        return new ResponseEntity<>(
                Response.builder()
                        .httpStatus(HttpStatus.CREATED)
                        .message(USER_CREATED.getValue())
                        .data(userResponse)
                        .build(), HttpStatus.CREATED);
    }

    @GetMapping
    public List<UserResponse> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("{userId}")
    public UserResponse getUserById(@PathVariable Integer userId) {
        log.info(USER_ID.getValue(), userId);
        return userService.getUserById(userId);
    }

    @PutMapping("{userId}")
    public ResponseEntity<Response> updateUser(@PathVariable Integer userId,
                                               @Valid @RequestBody UserRequest userRequest,
                                               @Valid @RequestParam @NotEmpty String userType) {
        log.info(USER_ID.getValue(), userId);
        log.info(USER_REQUEST.getValue(), userRequest);
        log.info(USER_TYPE.getValue(), userType);
        UserResponse userResponse = userService.updateUser(userId, userRequest, userType);
        return new ResponseEntity<>(
                Response.builder()
                        .httpStatus(HttpStatus.OK)
                        .message(USER_UPDATED.getValue())
                        .data(userResponse)
                        .build(), HttpStatus.OK);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Integer userId) {
        log.info(USER_ID.getValue(), userId);
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
