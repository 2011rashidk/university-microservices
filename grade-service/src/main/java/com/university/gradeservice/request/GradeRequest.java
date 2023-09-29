package com.university.gradeservice.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class GradeRequest {

    @PositiveOrZero
    private Integer minMarks;

    @Positive
    private Integer maxMarks;

    @NotEmpty
    private String grade;
}
