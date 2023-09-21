package com.university.userservice.enums;

public enum Constants {

    USER_REQUEST("user_request: {}"),
    USER_TYPE("userType: {}"),
    USER_CREATED("User created successfully"),
    USER_RETRIEVED("Users retrieved successfully"),
    USER_ID("userId: {},"),
    USER_UPDATED("User updated successfully"),
    USER_TYPE_NOT_FOUND("User type not found"),
    USER_RESPONSE("userResponse: {}"),
    USER_NOT_FOUND("User not found"),
    EXCEPTION("Exception: {}"),
    USER_TYPE_REQUEST("userTypeRequest: {}"),
    USER_TYPE_CREATED("User type created successfully"),
    USER_TYPE_RETRIEVED("User types retrieved successfully"),
    USER_TYPE_ID("userTypeId: {}"),
    USER_TYPE_UPDATED("User type updated successfully"),
    USER_TYPE_RESPONSE("userTypeResponse: {}");
    private final String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
