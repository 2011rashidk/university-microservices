package com.university.teacherservice.enums;

public enum Constants {


    TEACHER_PROFILE_REQUEST("teacherProfileRequest: {}"),
    SAVED_TEACHER_PROFILE("savedTeacherProfile: {}"),
    TEACHER_PROFILE_CREATED("Teacher profile created successfully"),
    TEACHER_ID("teacherId: {}"),
    TEACHER_PROFILE_RETRIEVED("Teacher profile retrieved successfully"),
    TEACHER_PROFILE_UPDATED("Teacher profile updated successfully"),
    TEACHER_PROFILE_DELETED("Teacher profile deleted successfully"),

    TEACHER_PROFILE("teacherProfile: {}"),
    TEACHER_PROFILE_NOT_FOUND("Teacher profile not found"),
    UPDATED_TEACHER_PROFILE("updatedTeacherProfile: {}"),

    EXCEPTION("Exception: {}"),
    TEACHER("TEACHER"),
    TEACHER_REQUEST("teacher_request: {}"),
    USER_ID("userId: {}"),
    RESPONSE("response: {}"),
    TEACHER_ACTIVATED("Teacher profile activated"),
    AVAILABLE_COURSES("Available courses: {}"),
    COURSE_NOT_FOUND("Any or none of the courses found!"),
    LIST_AFTER_ADDING_COURSE("List after adding course: "),
    TOTAL_COURSE_LIST("Total course list: {}"),
    COURSES_ASSIGNED("Course/s assigned successfully!"),
    COURSE_REMOVED("Course/s removed!"),
    COURSE_NOT_TAUGHT("Any or none of the courses taught!"),
    COURSES_TAUGHT("Courses taught: {}"),
    LIST_AFTER_REMOVING_COURSE("List after removing course: "),
    NOT_A_TEACHER("Given user not a teacher!");
    private final String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
