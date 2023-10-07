package com.university.userservice.service;

import com.university.userservice.entity.User;
import com.university.userservice.entity.UserType;
import com.university.userservice.exception.NotFoundException;
import com.university.userservice.repository.UserRepository;
import com.university.userservice.repository.UserTypeRepository;
import com.university.userservice.request.UserRequest;
import com.university.userservice.response.UserResponse;
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

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserTypeRepository userTypeRepository;

    @Mock
    private OktaRegistrationService oktaRegistrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstname("Rashid");
        userRequest.setLastname("K");
        userRequest.setEmail("rashid.k@hu.com");
        userRequest.setPassword("P@ssword");
        String userType = "STUDENT";

        UserType type = new UserType();
        type.setTypeName(userType);

        when(userTypeRepository.findByTypeName(userType)).thenReturn(type);
        when(oktaRegistrationService.registerOnOkta(userRequest, userType)).thenReturn("oktaUserId");

        User savedUser = new User();
        BeanUtils.copyProperties(userRequest, savedUser);
        savedUser.setUserType(type);
        savedUser.setActive(true);
        savedUser.setOktaRegistrationId("oktaUserId");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponse userResponse = userService.createUser(userRequest, userType);

        assertEquals("Rashid", userResponse.getFirstname());
        assertEquals("K", userResponse.getLastname());
    }

//    @Test
//    void testGetUsers() {
//        List<User> users = new ArrayList<>();
//        User user = new User();
//        user.setFirstname("Rashid");
//        user.setLastname("K");
//        users.add(user);
//
//        when(userRepository.findAll()).thenReturn(users);
//
//        List<UserResponse> userResponses = userService.getUsers();
//
//        assertEquals(1, userResponses.size());
//        assertEquals("Rashid", userResponses.get(0).getFirstname());
//        assertEquals("K", userResponses.get(0).getLastname());
//    }

    @Test
    void testGetUserById() {
        Integer userId = 1;
        User user = new User();
        user.setFirstname("Rashid");
        user.setLastname("K");
        UserType type = new UserType();
        type.setTypeName("STUDENT");
        user.setUserType(type);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserResponse userResponse = userService.getUserById(userId);

        assertEquals("Rashid", userResponse.getFirstname());
        assertEquals("K", userResponse.getLastname());
        assertEquals("STUDENT", userResponse.getUserType());
    }

    @Test
    void testGetUserByIdNegative() {
        Integer userId = 1;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUserById(userId));
    }

    @Test
    void testUpdateUser() {
        Integer userId = 1;
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstname("Rashid");
        userRequest.setLastname("K");
        userRequest.setEmail("rashid.k@hu.com");
        userRequest.setPassword("NewP@ssword");
        String userType = "STUDENT";

        UserType type = new UserType();
        type.setTypeName(userType);

        User existingUser = new User();
        existingUser.setUserId(userId);
        existingUser.setFirstname("Rashid");
        existingUser.setLastname("K");
        existingUser.setEmail("rashid.k@hu.com");
        existingUser.setUserType(type);
        existingUser.setActive(true);
        existingUser.setOktaRegistrationId("oktaUserId");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userTypeRepository.findByTypeName(userType)).thenReturn(type);

        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        UserResponse updatedUserResponse = userService.updateUser(userId, userRequest, userType);

        assertEquals("Rashid", updatedUserResponse.getFirstname());
        assertEquals("K", updatedUserResponse.getLastname());
    }

    @Test
    void testUpdateUserNegative() {
        Integer userId = 1;
        UserRequest userRequest = new UserRequest();
        String userType = "STUDENT";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.updateUser(userId, userRequest, userType));
    }

    @Test
    void testDeleteUserPositive() {
        Integer userId = 1;

        User existingUser = new User();
        existingUser.setUserId(userId);
        existingUser.setActive(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        userService.deleteUser(userId);

        assertFalse(existingUser.isActive());
    }

    @Test
    void testDeleteUserNegative() {
        Integer userId = 1;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.deleteUser(userId));

        verify(userRepository, never()).save(any(User.class));
    }
}