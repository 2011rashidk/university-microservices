package com.university.userservice.controller;

import com.university.userservice.request.UserTypeRequest;
import com.university.userservice.response.Response;
import com.university.userservice.response.UserTypeResponse;
import com.university.userservice.service.UserTypeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/university/user/type")
@Slf4j
public class UserTypeController {

    @Autowired
    UserTypeService userTypeService;

    @PostMapping
    public ResponseEntity<Response> createUserType(@Valid @RequestBody UserTypeRequest userTypeRequest) {
        log.info("userTypeRequest: {}", userTypeRequest);
        UserTypeResponse userTypeResponse = userTypeService.createUserType(userTypeRequest);
        return new ResponseEntity<>(Response.builder()
                .httpStatus(HttpStatus.CREATED)
                .message("User type created successfully")
                .data(userTypeResponse)
                .build(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response> getUserTypes() {
        List<UserTypeResponse> userTypeResponseList = userTypeService.getUserTypes();
        return new ResponseEntity<>(Response.builder()
                .httpStatus(HttpStatus.OK)
                .message("User types retrieved successfully")
                .data(userTypeResponseList)
                .build(), HttpStatus.OK);
    }

    @GetMapping("{userTypeId}")
    public ResponseEntity<Response> getUserTypeById(@PathVariable Integer userTypeId) {
        log.info("userTypeId: {}", userTypeId);
        UserTypeResponse userTypeResponse = userTypeService.getUserTypeById(userTypeId);
        return new ResponseEntity<>(Response.builder()
                .httpStatus(HttpStatus.OK)
                .message("User type retrieved successfully")
                .data(userTypeResponse)
                .build(), HttpStatus.OK);
    }

    @PutMapping("{userTypeId}")
    public ResponseEntity<Response> updateUserTypeById(@PathVariable Integer userTypeId,
                                                       @Valid @RequestBody UserTypeRequest userTypeRequest) {
        log.info("userTypeId: {}", userTypeId);
        log.info("userTypeRequest: {}", userTypeRequest);
        UserTypeResponse userTypeResponse = userTypeService.updateUserTypeById(userTypeId, userTypeRequest);
        return new ResponseEntity<>(Response.builder()
                .httpStatus(HttpStatus.OK)
                .message("User type updated successfully")
                .data(userTypeResponse)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("{userTypeId}")
    public ResponseEntity<HttpStatus> deleteUserTypeById(@PathVariable Integer userTypeId) {
        log.info("userTypeId: {}", userTypeId);
        userTypeService.deleteUserTypeById(userTypeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
