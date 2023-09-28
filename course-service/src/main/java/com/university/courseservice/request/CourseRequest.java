package com.university.courseservice.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CourseRequest {

    @NotEmpty
    private String courseName;
    @NotEmpty
    private String courseDuration;
    @NotEmpty
    private String courseLevel;
    @NotEmpty
    private String description;
    @Positive
    private Integer fees;
}
