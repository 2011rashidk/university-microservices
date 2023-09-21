package com.university.studentservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@Document("student_profile")
@ToString
public class StudentProfile {

    @Id
    private String studentId;

    @Indexed(unique = true)
    private Integer userId;

    private String qualification;

    private List<String> coursesEnrolled;

    private List<String> softSkills;

    private List<String> techSkills;

    private List<String> hobbies;

    private boolean isActive;

}
