package com.university.studentservice.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class CourseRequest {

    @NotEmpty
    private Set<String> courses;
}
