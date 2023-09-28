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
    STUDENT_ACTIVATED("Student profile activated"),
    AVAILABLE_COURSES("Available courses: {}"),
    COURSE_NOT_FOUND("Any or none of the courses found!"),
    LIST_AFTER_ENROLLING_COURSE("List after enrolling course: "),
    TOTAL_COURSE_LIST("Total course list: {}"),
    COURSES_ENROLLED("Course/s enrolled successfully!"),
    COURSE_REMOVED("Course/s removed!"),
    COURSE_NOT_ENROLLED("Any or none of the courses enrolled!"),
    COURSE_ENROLLED("Courses enrolled: {}"),
    LIST_AFTER_REMOVING_COURSE("List after removing course: "),
    NOT_A_STUDENT("Given user not a student!");
    private final String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
