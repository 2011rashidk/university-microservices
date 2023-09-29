package com.university.gradeservice.repository;

import com.university.gradeservice.entity.StudentGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentGradeRepository extends JpaRepository<StudentGrade, Integer> {
    List<StudentGrade> findByCourseId(Integer courseId);
}
