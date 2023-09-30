package com.university.gradeservice.controller;

import com.university.gradeservice.request.StudentGradeRequest;
import com.university.gradeservice.response.Response;
import com.university.gradeservice.response.StudentCourseGrade;
import com.university.gradeservice.response.StudentGradeResponse;
import com.university.gradeservice.service.StudentGradeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.university.gradeservice.enums.Constants.*;

@RestController
@RequestMapping("api/university/grade/student")
@Slf4j
public class StudentGradeController {

    @Autowired
    StudentGradeService studentGradeService;

    @PostMapping
    public ResponseEntity<Response> assignStudentGrade(@Valid @RequestBody StudentGradeRequest studentGradeRequest) {
        log.info(STUDENT_GRADE_REQUEST.getValue(), studentGradeRequest);
        StudentGradeResponse studentGradeResponse = studentGradeService.assignStudentGrade(studentGradeRequest);
        return new ResponseEntity<>(
                new Response(HttpStatus.CREATED, GRADE_ASSIGNED.getValue(), studentGradeResponse), HttpStatus.CREATED);
    }

    @GetMapping("{studentGradeId}")
    public StudentGradeResponse getStudentGrade(@PathVariable Integer studentGradeId) {
        log.info(STUDENT_GRADE_ID.getValue(), studentGradeId);
        return studentGradeService.getStudentGrade(studentGradeId);
    }

    @GetMapping("course/{courseId}")
    public StudentCourseGrade getGradeByCourse(@PathVariable Integer courseId) {
        log.info(COURSE_ID.getValue(), courseId);
        return studentGradeService.getGradeByCourse(courseId);
    }

}
