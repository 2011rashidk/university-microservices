package com.university.attendanceservice.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AttendanceRequest {

    @NotEmpty
    private String studentId;

    @Positive
    private Integer courseId;
}
