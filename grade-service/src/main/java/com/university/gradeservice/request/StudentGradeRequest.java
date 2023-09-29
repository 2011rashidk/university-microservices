package com.university.gradeservice.request;

import lombok.Data;

@Data
public class StudentGradeRequest {

    private String studentId;
    private Integer courseId;
    private Integer marks;
    private String comments;
}
