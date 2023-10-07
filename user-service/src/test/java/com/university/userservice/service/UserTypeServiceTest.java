package com.university.userservice.service;

import com.university.userservice.entity.UserType;
import com.university.userservice.exception.NotFoundException;
import com.university.userservice.repository.UserTypeRepository;
import com.university.userservice.request.UserTypeRequest;
import com.university.userservice.response.UserTypeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserTypeServiceTest {

    @InjectMocks
    private UserTypeService userTypeService;

    @Mock
    private UserTypeRepository userTypeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUserType() {
        UserTypeRequest userTypeRequest = new UserTypeRequest();
        userTypeRequest.setTypeName("STUDENT");

        UserType userType = new UserType();
        BeanUtils.copyProperties(userTypeRequest, userType);

        when(userTypeRepository.save(any(UserType.class))).thenReturn(userType);


        UserTypeResponse userTypeResponse = userTypeService.createUserType(userTypeRequest);

        assertEquals("STUDENT", userTypeResponse.getTypeName());
    }

    @Test
    void testGetUserTypes() {
        List<UserType> userTypes = new ArrayList<>();
        UserType userType = new UserType();
        userType.setTypeName("STUDENT");
        userTypes.add(userType);

        when(userTypeRepository.findAll()).thenReturn(userTypes);

        List<UserTypeResponse> userTypeResponses = userTypeService.getUserTypes();

        assertEquals(1, userTypeResponses.size());
        assertEquals("STUDENT", userTypeResponses.get(0).getTypeName());
    }

    @Test
    void testGetUserTypeByIdPositive() {
        Integer userTypeId = 1;
        UserType userType = new UserType();
        userType.setTypeName("STUDENT");

        when(userTypeRepository.findById(userTypeId)).thenReturn(Optional.of(userType));

        UserTypeResponse userTypeResponse = userTypeService.getUserTypeById(userTypeId);

        assertEquals("STUDENT", userTypeResponse.getTypeName());
    }

    @Test
    void testGetUserTypeByIdNegative() {
        Integer userTypeId = 1;

        when(userTypeRepository.findById(userTypeId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userTypeService.getUserTypeById(userTypeId));
    }

    @Test
    void testUpdateUserTypeByIdPositive() {
        Integer userTypeId = 1;
        UserTypeRequest userTypeRequest = new UserTypeRequest();
        userTypeRequest.setTypeName("TEACHER");

        UserType existingUserType = new UserType();
        existingUserType.setTypeId(userTypeId);
        existingUserType.setTypeName("STUDENT");

        when(userTypeRepository.findById(userTypeId)).thenReturn(Optional.of(existingUserType));

        UserType userType = new UserType();
        BeanUtils.copyProperties(userTypeRequest, userType);
        userType.setTypeId(userTypeId);

        when(userTypeRepository.save(any(UserType.class))).thenReturn(userType);

        UserTypeResponse updatedUserTypeResponse = userTypeService.updateUserTypeById(userTypeId, userTypeRequest);

        assertEquals("TEACHER", updatedUserTypeResponse.getTypeName());
    }

    @Test
    void testUpdateUserTypeByIdNegative() {
        Integer userTypeId = 1;
        UserTypeRequest userTypeRequest = new UserTypeRequest();
        userTypeRequest.setTypeName("TEACHER");

        when(userTypeRepository.findById(userTypeId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userTypeService.updateUserTypeById(userTypeId, userTypeRequest));

        verify(userTypeRepository, never()).save(any(UserType.class));
    }

    @Test
    void testDeleteUserTypeByIdPositive() {
        Integer userTypeId = 1;

        when(userTypeRepository.findById(userTypeId)).thenReturn(Optional.of(new UserType()));

        userTypeService.deleteUserTypeById(userTypeId);

        verify(userTypeRepository).deleteById(userTypeId);
    }

    @Test
    void testDeleteUserTypeByIdNegative() {
        Integer userTypeId = 1;

        when(userTypeRepository.findById(userTypeId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userTypeService.deleteUserTypeById(userTypeId));

        verify(userTypeRepository, never()).deleteById(userTypeId);
    }
}