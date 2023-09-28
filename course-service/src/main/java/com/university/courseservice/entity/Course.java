package com.university.courseservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "courses")
@Setter
@Getter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "course_duration")
    private String courseDuration;

    @Column(name = "course_level")
    private String courseLevel;

    @Column(name = "description")
    private String description;

    @Column(name = "fees")
    private Integer fees;

    @Column(name = "is_active")
    private boolean isActive;
}
