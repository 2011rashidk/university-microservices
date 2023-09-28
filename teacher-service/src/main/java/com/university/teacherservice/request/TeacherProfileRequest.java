package com.university.teacherservice.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class TeacherProfileRequest {

    @Positive
    private Integer userId;

    @NotEmpty
    private String qualification;

    @NotEmpty
    private List<String> certifications;

    @NotEmpty
    private List<String> languages;

}
