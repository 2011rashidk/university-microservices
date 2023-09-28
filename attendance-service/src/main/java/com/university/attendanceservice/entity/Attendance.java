package com.university.attendanceservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@Table(name = "attendance_report")
@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private Integer attendanceId;

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "class_date")
    private Date classDate;

    @Column(name = "is_present")
    private boolean isPresent;

}
