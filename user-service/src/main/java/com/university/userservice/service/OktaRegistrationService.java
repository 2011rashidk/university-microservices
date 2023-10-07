package com.university.userservice.service;

import com.university.userservice.exception.NotFoundException;
import com.university.userservice.request.UserRequest;
import com.university.userservice.response.OktaRegistrationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.university.userservice.enums.Constants.*;

@Service
@Slf4j
public class OktaRegistrationService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${okta.user.registration.api.url}")
    private String oktaRegistrationUrl;

    @Value("${okta.admin.group.id}")
    private String oktaAdminId;

    @Value("${okta.teacher.group.id}")
    private String oktaTeacherId;

    @Value("${okta.student.group.id}")
    private String oktaStudentId;

    @Value("${okta.api.token}")
    private String oktaApiToken;

    public String registerOnOkta(UserRequest userRequest, String userType) {

        String groupId = "";
        switch (userType) {
            case "ADMIN" -> groupId = oktaAdminId;
            case "TEACHER" -> groupId = oktaTeacherId;
            case "STUDENT" -> groupId = oktaStudentId;
            default -> throw new NotFoundException("Unexpected value: " + userType);
        }

        Map<String, Object> oktaRegRequest = new HashMap<>();
        Map<String, String> profileMap = new HashMap<>();
        Map<String, Map<String, String>> credentialsMap = new HashMap<>();
        Map<String, String> passwordValue = new HashMap<>();
        profileMap.put("firstName", userRequest.getFirstname());
        profileMap.put("lastName", userRequest.getLastname());
        profileMap.put("email", userRequest.getEmail());
        profileMap.put("login", userRequest.getEmail());
        passwordValue.put("value", userRequest.getPassword());
        credentialsMap.put("password", passwordValue);
        oktaRegRequest.put("profile", profileMap);
        oktaRegRequest.put("credentials", credentialsMap);
        oktaRegRequest.put("groupIds", List.of(groupId));

        log.info(OKTA_REGISTRATION_REQUEST.getValue(), oktaRegRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "SSWS ".concat(oktaApiToken));
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(oktaRegRequest, headers);

        String uriString = UriComponentsBuilder
                .fromUriString(oktaRegistrationUrl)
                .queryParam("activate", true).toUriString();

        ResponseEntity<OktaRegistrationResponse> oktaRegistrationResponse =
                restTemplate.postForEntity(uriString, httpEntity, OktaRegistrationResponse.class);

        if (oktaRegistrationResponse.getStatusCode().isSameCodeAs(HttpStatus.OK)) {
            return oktaRegistrationResponse.getBody().getId();
        }
        return USER_NOT_REGISTERED_ON_OKTA.getValue();
    }

}
