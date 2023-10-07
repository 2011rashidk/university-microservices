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

import static com.university.userservice.enums.Constants.*;

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
        log.info(USER_TYPE_RESPONSE.getValue(), userTypeResponse);
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
        log.info(USER_TYPE_RESPONSE.getValue(), userTypeResponseList);
        return userTypeResponseList;
    }


    public UserTypeResponse getUserTypeById(Integer userTypeId) {
        UserType userType = userTypeRepository.findById(userTypeId).orElseThrow(() -> new NotFoundException(USER_TYPE_NOT_FOUND.getValue()));
        UserTypeResponse userTypeResponse = new UserTypeResponse();
        BeanUtils.copyProperties(userType, userTypeResponse);
        log.info(USER_TYPE_RESPONSE.getValue(), userTypeResponse);
        return userTypeResponse;
    }


    public UserTypeResponse updateUserTypeById(Integer userTypeId, UserTypeRequest userTypeRequest) {
        boolean isEmpty = userTypeRepository.findById(userTypeId).isEmpty();
        if (isEmpty) {
            log.error(USER_TYPE_NOT_FOUND.getValue());
            throw new NotFoundException(USER_TYPE_NOT_FOUND.getValue());
        }
        UserType userType = new UserType();
        BeanUtils.copyProperties(userTypeRequest, userType);
        userType.setTypeId(userTypeId);
        UserType updatedUserType = userTypeRepository.save(userType);
        UserTypeResponse userTypeResponse = new UserTypeResponse();
        BeanUtils.copyProperties(updatedUserType, userTypeResponse);
        log.info(USER_TYPE_RESPONSE.getValue(), userTypeResponse);
        return userTypeResponse;
    }

    public void deleteUserTypeById(Integer userTypeId) {
        boolean isEmpty = userTypeRepository.findById(userTypeId).isEmpty();
        if (isEmpty) {
            log.error(USER_TYPE_NOT_FOUND.getValue());
            throw new NotFoundException(USER_TYPE_NOT_FOUND.getValue());
        }
        userTypeRepository.deleteById(userTypeId);
    }

}
