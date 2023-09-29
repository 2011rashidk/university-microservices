package com.university.gradeservice.service;

import com.university.gradeservice.client.CourseServiceClient;
import com.university.gradeservice.client.StudentServiceClient;
import com.university.gradeservice.entity.StudentGrade;
import com.university.gradeservice.exception.BadRequestException;
import com.university.gradeservice.exception.NotFoundException;
import com.university.gradeservice.repository.StudentGradeRepository;
import com.university.gradeservice.request.StudentGradeRequest;
import com.university.gradeservice.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.university.gradeservice.enums.Constants.*;

@Service
@Slf4j
public class StudentGradeService {

    @Autowired
    StudentGradeRepository studentGradeRepository;

    @Autowired
    GradeService gradeService;

    @Autowired
    StudentServiceClient studentServiceClient;

    @Autowired
    CourseServiceClient courseServiceClient;

    public StudentGradeResponse assignStudentGrade(StudentGradeRequest studentGradeRequest) {
        StudentProfile studentProfile = studentServiceClient.getProfile(studentGradeRequest.getStudentId());
        Set<String> coursesEnrolled = studentProfile.getCoursesEnrolled();
        log.info(COURSES_ENROLLED.getValue(), coursesEnrolled);
        CourseResponse course = courseServiceClient.getCourseById(studentGradeRequest.getCourseId());
        String courseName = course.getCourseName();
        log.info(GIVEN_COURSE.getValue(), courseName);
        if (!coursesEnrolled.contains(courseName)) {
            throw new BadRequestException(COURSE_NOT_ENROLLED.getValue());
        }
        StudentGrade studentGrade = new StudentGrade();
        BeanUtils.copyProperties(studentGradeRequest, studentGrade);
        String grade = gradeService.getGradeWithMarks(studentGradeRequest.getMarks());
        studentGrade.setGrade(grade);
        StudentGrade savedStudentGrade = studentGradeRepository.save(studentGrade);
        log.info(SAVED_STUDENT_GRADE.getValue(), savedStudentGrade);
        StudentGradeResponse studentGradeResponse = new StudentGradeResponse();
        BeanUtils.copyProperties(savedStudentGrade, studentGradeResponse);
        return studentGradeResponse;
    }

    public StudentGradeResponse getStudentGrade(Integer studentGradeId) {
        StudentGrade studentGrade = studentGradeRepository.findById(studentGradeId)
                .orElseThrow(() -> new NotFoundException(NO_RECORD_FOUND.getValue()));
        StudentGradeResponse studentGradeResponse = new StudentGradeResponse();
        BeanUtils.copyProperties(studentGrade, studentGradeResponse);
        log.info(STUDENT_GRADE_RESPONSE.getValue(), studentGradeResponse);
        return studentGradeResponse;
    }

    public StudentCourseGrade getGradeByCourse(Integer courseId) {
        CourseResponse course = courseServiceClient.getCourseById(courseId);
        String courseName = course.getCourseName();
        StudentCourseGrade studentCourseGrade = new StudentCourseGrade();
        studentCourseGrade.setCourseId(courseId);
        studentCourseGrade.setCourseName(courseName);
        List<StudentGrade> studentGradeList = studentGradeRepository.findByCourseId(courseId);
        log.info(STUDENT_GRADE_LIST.getValue(), studentGradeList);
        TreeMap<String, String> studentIdGrade = new TreeMap<>();
        for (StudentGrade studentGrade : studentGradeList) {
            String studentName = getStudentName(studentGrade.getStudentId());
            studentIdGrade.put(studentName, studentGrade.getGrade());
        }
        studentCourseGrade.setStudentGrade(studentIdGrade);
        log.info(STUDENT_COURSE_GRADE.getValue(), studentCourseGrade);
        return studentCourseGrade;
    }

    private String getStudentName(String studentId) {
        UserInfo userInfo = studentServiceClient.getProfile(studentId).getUserInfo();
        return userInfo.getFirstname().concat(SPACE.getValue()).concat(userInfo.getLastname());
    }

}
