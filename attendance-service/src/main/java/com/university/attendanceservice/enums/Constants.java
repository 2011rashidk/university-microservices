package com.university.attendanceservice.enums;

public enum Constants {
    EXCEPTION("Exception: {}"),
    ATTENDANCE_REQUEST("attendanceRequest: {}"),
    ATTENDANCE_POSTED("Attendance posted successfully!"),
    ATTENDANCE_RESPONSE("Attendance response: {}"),
    ATTENDANCE_ID("attendanceId: {}"),
    NO_RECORD_FOUND("No attendance record found!"),
    COURSE_ID("courseId: {}"),
    FROM_DATE("fromDate: {}"),
    TO_DATE("toDate: {}"),
    SPACE(" "),
    COURSE_ATTENDANCE_DETAILS("Course attendance details: {}"),
    ATTENDANCE_LIST("attendanceList: {}");

    private final String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
