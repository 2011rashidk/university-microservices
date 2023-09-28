package com.university.attendanceservice.response;

import lombok.Data;

import java.util.Date;

@Data
public class AttendanceResponse {

    private Integer attendanceId;
    private String studentId;
    private Integer courseId;
    private Date classDate;
    private boolean isPresent;
}
