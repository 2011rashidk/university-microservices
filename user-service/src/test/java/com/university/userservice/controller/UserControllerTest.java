package com.university.userservice.controller;

import com.university.userservice.request.UserRequest;
import com.university.userservice.response.Response;
import com.university.userservice.response.UserResponse;
import com.university.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        UserRequest userRequest = new UserRequest();
        String userType = "student";
        UserResponse userResponse = new UserResponse();

        when(userService.createUser(userRequest, userType)).thenReturn(userResponse);

        ResponseEntity<Response> responseEntity = userController.createUser(userRequest, userType);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("User created successfully", responseEntity.getBody().getMessage());
        assertEquals(userResponse, responseEntity.getBody().getData());
    }

    @Test
    void testGetUsers() {
        List<UserResponse> userResponses = Collections.singletonList(new UserResponse());

        when(userService.getUsers("STUDENT", 0, 5)).thenReturn(userResponses);

        List<UserResponse> result = userController.getUsers("STUDENT", 0, 5);

        assertEquals(userResponses, result);
    }

    @Test
    void testGetUsersForNullRequest() {
        List<UserResponse> userResponses = Collections.singletonList(new UserResponse());

        when(userService.getUsers(null, null, null)).thenReturn(userResponses);

        List<UserResponse> result = userController.getUsers(null, null, null);

        assertEquals(userResponses, result);
    }

    @Test
    void testGetUserById() {
        Integer userId = 1;
        UserResponse expectedUser = new UserResponse();

        when(userService.getUserById(userId)).thenReturn(expectedUser);

        UserResponse actualUser = userController.getUserById(userId);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void testUpdateUser() {
        Integer userId = 1;
        UserRequest userRequest = new UserRequest();
        String userType = "student";
        UserResponse userResponse = new UserResponse();

        when(userService.updateUser(userId, userRequest, userType)).thenReturn(userResponse);

        ResponseEntity<Response> responseEntity = userController.updateUser(userId, userRequest, userType);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("User updated successfully", responseEntity.getBody().getMessage());
        assertEquals(userResponse, responseEntity.getBody().getData());
    }

    @Test
    void testDeleteUser() {
        Integer userId = 1;

        ResponseEntity<HttpStatus> responseEntity = userController.deleteUser(userId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}
