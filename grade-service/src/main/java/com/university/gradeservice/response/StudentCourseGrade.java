package com.university.gradeservice.response;

import lombok.Data;

import java.util.TreeMap;

@Data
public class StudentCourseGrade {

    private Integer courseId;
    private String courseName;
    private TreeMap<String, String> studentGrade;
}
