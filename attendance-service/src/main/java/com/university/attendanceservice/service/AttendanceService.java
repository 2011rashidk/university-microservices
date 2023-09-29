package com.university.attendanceservice.service;

import com.university.attendanceservice.client.CourseServiceClient;
import com.university.attendanceservice.client.StudentServiceClient;
import com.university.attendanceservice.entity.Attendance;
import com.university.attendanceservice.exception.NotFoundException;
import com.university.attendanceservice.repository.AttendanceRepository;
import com.university.attendanceservice.request.AttendanceRequest;
import com.university.attendanceservice.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.university.attendanceservice.enums.Constants.*;

@Service
@Slf4j
public class AttendanceService {

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    CourseServiceClient courseServiceClient;

    @Autowired
    StudentServiceClient studentServiceClient;

    public AttendanceResponse postAttendance(AttendanceRequest attendanceRequest) {
        studentServiceClient.getProfile(attendanceRequest.getStudentId());
        courseServiceClient.getCourseById(attendanceRequest.getCourseId());
        Attendance attendance = new Attendance();
        BeanUtils.copyProperties(attendanceRequest, attendance);
        attendance.setClassDate(Date.valueOf(LocalDate.now()));
        attendance.setPresent(true);
        Attendance postedAttendance = attendanceRepository.save(attendance);
        AttendanceResponse attendanceResponse = new AttendanceResponse();
        BeanUtils.copyProperties(postedAttendance, attendanceResponse);
        log.info(ATTENDANCE_RESPONSE.getValue(), attendanceResponse);
        return attendanceResponse;
    }

    public AttendanceResponse getAttendance(Integer attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new NotFoundException(NO_RECORD_FOUND.getValue()));
        AttendanceResponse attendanceResponse = new AttendanceResponse();
        BeanUtils.copyProperties(attendance, attendanceResponse);
        log.info(ATTENDANCE_RESPONSE.getValue(), attendanceResponse);
        return attendanceResponse;
    }

    public CourseAttendanceDetails getAttendanceByCourse(Integer courseId, java.util.Date fromDate, java.util.Date toDate) {
        CourseAttendanceDetails courseAttendanceDetails = new CourseAttendanceDetails();
        CourseResponse course = courseServiceClient.getCourseById(courseId);
        courseAttendanceDetails.setCourseId(courseId);
        courseAttendanceDetails.setCourseName(course.getCourseName());
        TreeMap<String, List<String>> dateWiseAttendanceWithName = getDateWiseAttendance(courseId, fromDate, toDate);
        courseAttendanceDetails.setDateWiseAttendance(dateWiseAttendanceWithName);
        log.info(COURSE_ATTENDANCE_DETAILS.getValue(), courseAttendanceDetails);
        return courseAttendanceDetails;
    }

    private TreeMap<String, List<String>> getDateWiseAttendance(Integer courseId, java.util.Date fromDate, java.util.Date toDate) {
        List<Attendance> attendanceList;
        if (fromDate == null && toDate == null) {
            attendanceList = attendanceRepository.findByCourseId(courseId);
        } else {
            attendanceList = attendanceRepository.getAttendanceByCourseWithDate(courseId, fromDate, toDate);
        }
        Map<java.util.Date, List<String>> dateWiseAttendance = attendanceList.stream()
                .collect(Collectors.groupingBy(Attendance::getClassDate,
                        Collectors.mapping(Attendance::getStudentId, Collectors.toList())));
        log.info(DATE_WISE_ATTENDANCE.getValue(), dateWiseAttendance);
        TreeMap<String, List<String>> dateWiseAttendanceWithName = new TreeMap<>();
        dateWiseAttendance.forEach((key, value) -> {
            List<String> studentNameList = new ArrayList<>();
            for (String studentId : value) {
                String studentName = getStudentName(studentId);
                studentNameList.add(studentName);
            }
            dateWiseAttendanceWithName.put(key.toString(), studentNameList);
        });
        return dateWiseAttendanceWithName;
    }

    private String getStudentName(String studentId) {
        UserInfo userInfo = studentServiceClient.getProfile(studentId).getUserInfo();
        return userInfo.getFirstname().concat(SPACE.getValue()).concat(userInfo.getLastname());
    }

}
