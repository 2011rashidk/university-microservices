package com.university.attendanceservice.response;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CourseAttendanceDetails {

    private Integer courseId;
    private String courseName;
    private Map<String, List<String>> dateWiseAttendance;
}
