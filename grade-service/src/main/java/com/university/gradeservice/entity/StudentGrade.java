package com.university.gradeservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "student_grade",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"}))
public class StudentGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_grade_id")
    private Integer studentGradeId;

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "grade")
    private String grade;

    @Column(name = "comments")
    private String comments;
}
