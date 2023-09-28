package com.university.studentservice.response;

import lombok.Data;

@Data
public class UserInfo {

    private String firstname;
    private String lastname;
    private Integer age;
    private String email;
    private String mobile;
    private String address;
    private String userType;
}
