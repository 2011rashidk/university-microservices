package com.university.studentservice.enums;

public enum Constants {


    STUDENT_PROFILE_REQUEST("studentProfileRequest: {}"),
    SAVED_STUDENT_PROFILE("savedStudentProfile: {}"),
    STUDENT_PROFILE_CREATED("Student profile created successfully"),
    STUDENT_ID("studentId: {}"),
    STUDENT_PROFILE_RETRIEVED("Student profile retrieved successfully"),
    STUDENT_PROFILE_UPDATED("Student profile updated successfully"),
    STUDENT_PROFILE_DELETED("Student profile deleted successfully"),

    STUDENT_PROFILE("studentProfile: {}"),
    STUDENT_PROFILE_NOT_FOUND("Student profile not found"),
    UPDATED_STUDENT_PROFILE("updatedStudentProfile: {}"),

    EXCEPTION("Exception: {}"),
    STUDENT("STUDENT"),
    STUDENT_REQUEST("student_request: {}"),
    USER_ID("userId: {}"),
    RESPONSE("response: {}"),
    STUDENT_ACTIVATED("Student profile activated");
    private final String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
