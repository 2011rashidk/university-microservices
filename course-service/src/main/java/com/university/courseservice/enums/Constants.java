package com.university.courseservice.enums;

public enum Constants {
    EXCEPTION("Exception: {}"),
    COURSE_REQUEST("courseRequest: {}"),
    COURSE_ID("courseId: {}"),
    COURSE_UPDATED("Course updated successfully"),
    COURSE_CREATED("Course created successfully"),
    COURSE_RESPONSE("courseResponse: {}"),
    NO_COURSE_FOUND("No course found");

    private final String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
