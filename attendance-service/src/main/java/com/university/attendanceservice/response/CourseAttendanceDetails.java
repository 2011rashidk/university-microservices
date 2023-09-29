package com.university.attendanceservice.response;

import lombok.Data;

import java.util.List;
import java.util.TreeMap;

@Data
public class CourseAttendanceDetails {

    private Integer courseId;
    private String courseName;
    private TreeMap<String, List<String>> dateWiseAttendance;
}
