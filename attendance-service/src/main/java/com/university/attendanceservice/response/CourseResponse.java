package com.university.attendanceservice.response;

import lombok.Data;

@Data
public class CourseResponse {

    private Integer courseId;
    private String courseName;
    private String courseDuration;
    private String courseLevel;
    private String description;
    private Integer fees;
}
