package com.university.userservice.request;

import lombok.Data;

@Data
public class UserRequest {

    private String firstname;
    private String lastname;
    private Integer age;
    private String email;
    private String mobile;
    private String address;
}
