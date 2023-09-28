package com.university.teacherservice.response;

import lombok.Data;

@Data
public class CourseResponse {

    private Integer courseId;
    private String courseName;
    private String courseDuration;
    private String courseLevel;
    private String description;
}
