package com.university.userservice.service;

import com.university.userservice.entity.UserType;
import com.university.userservice.exception.NotFoundException;
import com.university.userservice.repository.UserTypeRepository;
import com.university.userservice.request.UserTypeRequest;
import com.university.userservice.response.UserTypeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserTypeService {

    @Autowired
    UserTypeRepository userTypeRepository;

    public UserTypeResponse createUserType(UserTypeRequest userTypeRequest) {
        UserType userType = new UserType();
        BeanUtils.copyProperties(userTypeRequest, userType);
        UserType savedUserType = userTypeRepository.save(userType);
        UserTypeResponse userTypeResponse = new UserTypeResponse();
        BeanUtils.copyProperties(savedUserType, userTypeResponse);
        log.info("userTypeResponse: {}", userTypeResponse);
        return userTypeResponse;
    }

    public List<UserTypeResponse> getUserTypes() {
        List<UserType> userTypeList = userTypeRepository.findAll();
        List<UserTypeResponse> userTypeResponseList = new ArrayList<>();
        for (UserType userType : userTypeList) {
            UserTypeResponse userTypeResponse = new UserTypeResponse();
            BeanUtils.copyProperties(userType, userTypeResponse);
            userTypeResponseList.add(userTypeResponse);
        }
        log.info("userTypeResponse: {}", userTypeResponseList);
        return userTypeResponseList;
    }


    public UserTypeResponse getUserTypeById(Integer userTypeId) {
        UserType userType = userTypeRepository.findById(userTypeId).orElseThrow(() -> new NotFoundException("NO_DATA_FOUND: " + userTypeId));
        UserTypeResponse userTypeResponse = new UserTypeResponse();
        BeanUtils.copyProperties(userType, userTypeResponse);
        log.info("userTypeResponse: {}", userTypeResponse);
        return userTypeResponse;
    }


    public UserTypeResponse updateUserTypeById(Integer userTypeId, UserTypeRequest userTypeRequest) {
        userTypeRepository.findById(userTypeId).orElseThrow(() -> new NotFoundException("NO_DATA_FOUND: " + userTypeId));
        UserType userType = new UserType();
        BeanUtils.copyProperties(userTypeRequest, userType);
        userType.setTypeId(userTypeId);
        UserType updatedUserType = userTypeRepository.save(userType);
        UserTypeResponse userTypeResponse = new UserTypeResponse();
        BeanUtils.copyProperties(updatedUserType, userTypeResponse);
        log.info("userTypeResponse: {}", userTypeResponse);
        return userTypeResponse;
    }

    public void deleteUserTypeById(Integer userTypeId) {
        userTypeRepository.findById(userTypeId).orElseThrow(() -> new NotFoundException("NO_DATA_FOUND: " + userTypeId));
        userTypeRepository.deleteById(userTypeId);
    }

}
