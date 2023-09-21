package com.university.studentservice.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class StudentProfileRequest {

    @Positive
    private Integer userId;

    @NotEmpty
    private String qualification;

    @NotEmpty
    private List<String> coursesEnrolled;

    @NotEmpty
    private List<String> softSkills;

    @NotEmpty
    private List<String> techSkills;

    @NotEmpty
    private List<String> hobbies;
}
