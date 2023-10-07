package com.university.userservice.controller;

import com.university.userservice.request.UserTypeRequest;
import com.university.userservice.response.Response;
import com.university.userservice.response.UserTypeResponse;
import com.university.userservice.service.UserTypeService;
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
import static org.mockito.Mockito.*;

class UserTypeControllerTest {

    @InjectMocks
    private UserTypeController userTypeController;

    @Mock
    private UserTypeService userTypeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUserType() {
        UserTypeRequest userTypeRequest = new UserTypeRequest();
        UserTypeResponse userTypeResponse = new UserTypeResponse();

        when(userTypeService.createUserType(userTypeRequest)).thenReturn(userTypeResponse);

        ResponseEntity<Response> responseEntity = userTypeController.createUserType(userTypeRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("User type created successfully", responseEntity.getBody().getMessage());
        assertEquals(userTypeResponse, responseEntity.getBody().getData());
    }

    @Test
    void testGetUserTypes() {
        List<UserTypeResponse> expectedUserTypes = Collections.singletonList(new UserTypeResponse());

        when(userTypeService.getUserTypes()).thenReturn(expectedUserTypes);

        ResponseEntity<Response> responseEntity = userTypeController.getUserTypes();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("User type retrieved successfully", responseEntity.getBody().getMessage());
        assertEquals(expectedUserTypes, responseEntity.getBody().getData());
    }

    @Test
    void testGetUserTypeById() {
        Integer userTypeId = 1;
        UserTypeResponse expectedUserType = new UserTypeResponse();

        when(userTypeService.getUserTypeById(userTypeId)).thenReturn(expectedUserType);

        ResponseEntity<Response> responseEntity = userTypeController.getUserTypeById(userTypeId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("User type retrieved successfully", responseEntity.getBody().getMessage());
        assertEquals(expectedUserType, responseEntity.getBody().getData());
    }

    @Test
    void testUpdateUserTypeById() {
        Integer userTypeId = 1;
        UserTypeRequest userTypeRequest = new UserTypeRequest();
        UserTypeResponse userTypeResponse = new UserTypeResponse();

        when(userTypeService.updateUserTypeById(userTypeId, userTypeRequest)).thenReturn(userTypeResponse);

        ResponseEntity<Response> responseEntity = userTypeController.updateUserTypeById(userTypeId, userTypeRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("User type updated successfully", responseEntity.getBody().getMessage());
        assertEquals(userTypeResponse, responseEntity.getBody().getData());
    }

    @Test
    void testDeleteUserTypeById() {
        Integer userTypeId = 1;

        ResponseEntity<HttpStatus> responseEntity = userTypeController.deleteUserTypeById(userTypeId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}