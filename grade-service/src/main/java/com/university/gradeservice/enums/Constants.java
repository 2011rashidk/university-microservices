package com.university.gradeservice.enums;

public enum Constants {

    GRADE_REQUEST("gradeRequest: {}"),
    EXCEPTION("Exception: {}"),
    GRADED_ADDED("Grade added successfully!"),
    GRADE_ID("gradeId: {}"),
    GRADED_UPDATED("Grade updated successfully!"),
    GRADE_RESPONSE("gradeResponse: {}"),
    GRADE_NOT_FOUND("Grade not found for given ID!"),
    STUDENT_GRADE_REQUEST("studentGradeRequest: {}"),
    GRADE_ASSIGNED("Grade assigned successfully!"),
    STUDENT_GRADE_ID("studentGradeId: {}"),
    SAVED_STUDENT_GRADE("savedStudentGrade: {}"),
    NO_RECORD_FOUND("Student grade record not found!"),
    STUDENT_GRADE_RESPONSE("studentGradeResponse: {}"),
    COURSES_ENROLLED("coursesEnrolled: {}"),
    GIVEN_COURSE("givenCourse: {}"),
    COURSE_NOT_ENROLLED("Given course not enrolled by student!"),
    COURSE_ID("courseId: {}"),
    STUDENT_GRADE_LIST("studentGradeList: {}"),
    STUDENT_COURSE_GRADE("studentCourseGrade: {}"),
    SPACE(" ");

    private final String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
