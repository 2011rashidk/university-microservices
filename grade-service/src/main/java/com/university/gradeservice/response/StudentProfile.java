package com.university.gradeservice.response;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class StudentProfile {

    private String studentId;
    private Integer userId;
    private UserInfo userInfo;
    private String qualification;
    private Set<String> coursesEnrolled;
    private List<String> softSkills;
    private List<String> techSkills;
    private List<String> hobbies;
    private boolean isActive;
}
