package com.university.teacherservice.model;

import com.university.teacherservice.response.UserInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@Document("teacher_profile")
@ToString
public class TeacherProfile {

    @Id
    private String teacherId;

    @Indexed(unique = true)
    private Integer userId;

    private UserInfo userInfo;

    private String qualification;

    private Set<String> coursesTaught;

    private List<String> certifications;

    private List<String> languages;

    private boolean isActive;

}
