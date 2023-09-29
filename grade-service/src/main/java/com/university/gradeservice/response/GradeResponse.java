package com.university.gradeservice.response;

import lombok.Data;

@Data
public class GradeResponse {

    private Integer gradeId;
    private Integer minMarks;
    private Integer maxMarks;
    private String grade;
}
