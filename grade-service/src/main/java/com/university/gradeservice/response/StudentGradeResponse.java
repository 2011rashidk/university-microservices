package com.university.gradeservice.response;

import lombok.Data;

@Data
public class StudentGradeResponse {

    private Integer studentGradeId;
    private String studentId;
    private Integer courseId;
    private String grade;
    private String comments;
}
