package com.university.userservice.service;

import com.university.userservice.entity.User;
import com.university.userservice.entity.UserType;
import com.university.userservice.exception.NotFoundException;
import com.university.userservice.repository.UserRepository;
import com.university.userservice.repository.UserTypeRepository;
import com.university.userservice.request.UserRequest;
import com.university.userservice.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.university.userservice.enums.Constants.*;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTypeRepository userTypeRepository;

    @Autowired
    OktaRegistrationService oktaRegistrationService;

    public UserResponse createUser(UserRequest userRequest, String userType) {
        UserType type = userTypeRepository.findByTypeName(userType);
        if (type == null) {
            throw new NotFoundException(USER_TYPE_NOT_FOUND.getValue());
        }
        String oktaRegistrationId = oktaRegistrationService.registerOnOkta(userRequest, userType);
        log.info(OKTA_REGISTRATION_ID.getValue(), oktaRegistrationId);
        User user = new User();
        BeanUtils.copyProperties(userRequest, user);
        user.setUserType(type);
        user.setActive(true);
        user.setOktaRegistrationId(oktaRegistrationId);
        User savedUser = userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(savedUser, userResponse);
        userResponse.setUserType(savedUser.getUserType().getTypeName());
        log.info(USER_RESPONSE.getValue(), userResponse);
        return userResponse;
    }

    public List<UserResponse> getUsers(Integer pageNo, Integer pageSize) {
        Pageable pageable;
        if (pageNo == null || pageSize == null) {
            pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(pageNo, pageSize);
        }
        Page<User> users = userRepository.findAll(pageable);
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            UserResponse userResponse = new UserResponse();
            BeanUtils.copyProperties(user, userResponse);
            userResponse.setUserType(user.getUserType().getTypeName());
            userResponses.add(userResponse);
        }
        log.info(USER_RESPONSE.getValue(), userResponses);
        return userResponses;
    }

    public UserResponse getUserById(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getValue()));
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);
        userResponse.setUserType(user.getUserType().getTypeName());
        log.info(USER_RESPONSE.getValue(), userResponse);
        return userResponse;
    }

    public UserResponse updateUser(Integer userId, UserRequest userRequest, String userType) {
        User existingUserData = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getValue()));
        UserType type = userTypeRepository.findByTypeName(userType);
        if (type == null) {
            throw new NotFoundException(USER_TYPE_NOT_FOUND.getValue());
        }
        User user = new User();
        BeanUtils.copyProperties(userRequest, user);
        user.setUserId(userId);
        user.setUserType(type);
        user.setActive(true);
        user.setOktaRegistrationId(existingUserData.getOktaRegistrationId());
        User updatedUser = userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(updatedUser, userResponse);
        log.info(USER_RESPONSE.getValue(), userResponse);
        return userResponse;
    }

    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getValue()));
        user.setActive(false);
        userRepository.save(user);
    }

}
