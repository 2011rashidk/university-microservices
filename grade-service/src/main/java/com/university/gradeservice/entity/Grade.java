package com.university.gradeservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "grades")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private Integer gradeId;

    @Column(name = "min_marks")
    private Integer minMarks;

    @Column(name = "max_marks")
    private Integer maxMarks;

    @Column(name = "grade")
    private String grade;
}
