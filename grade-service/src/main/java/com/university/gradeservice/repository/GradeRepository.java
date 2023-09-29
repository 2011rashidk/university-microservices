package com.university.gradeservice.repository;

import com.university.gradeservice.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {

    @Query(value = "select grade from grades where :scoredMarks >= min_marks and :scoredMarks <= max_marks",
            nativeQuery = true)
    String getGradeWithMarks(Integer scoredMarks);

}
