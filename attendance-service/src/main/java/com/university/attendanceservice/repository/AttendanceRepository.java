package com.university.attendanceservice.repository;

import com.university.attendanceservice.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    @Query(value = "select * from attendance_report where course_id = :courseId and class_date between :fromDate and :toDate",
            nativeQuery = true)
    List<Attendance> getAttendanceByCourseWithDate(Integer courseId, Date fromDate, Date toDate);

    @Query(value = "select * from attendance_report where course_id = :courseId", nativeQuery = true)
    List<Attendance> getAttendanceByCourse(Integer courseId);
}
