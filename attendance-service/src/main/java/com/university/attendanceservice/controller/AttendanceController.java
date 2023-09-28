package com.university.attendanceservice.controller;

import com.university.attendanceservice.request.AttendanceRequest;
import com.university.attendanceservice.response.AttendanceResponse;
import com.university.attendanceservice.response.CourseAttendanceDetails;
import com.university.attendanceservice.response.Response;
import com.university.attendanceservice.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static com.university.attendanceservice.enums.Constants.*;

@RestController
@RequestMapping("api/university/attendance")
@Slf4j
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

    @PostMapping
    public ResponseEntity<Response> postAttendance(@Valid @RequestBody AttendanceRequest attendanceRequest) {
        log.info(ATTENDANCE_REQUEST.getValue(), attendanceRequest);
        AttendanceResponse attendanceResponse = attendanceService.postAttendance(attendanceRequest);
        return new ResponseEntity<>(
                new Response(HttpStatus.CREATED, ATTENDANCE_POSTED.getValue(), attendanceResponse), HttpStatus.CREATED);
    }

    @GetMapping("{attendanceId}")
    public AttendanceResponse getAttendance(@PathVariable Integer attendanceId) {
        log.info(ATTENDANCE_ID.getValue(), attendanceId);
        return attendanceService.getAttendance(attendanceId);
    }

    @GetMapping("course/{courseId}")
    public CourseAttendanceDetails getAttendanceByCourse(@PathVariable Integer courseId,
                                                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
                                                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
        log.info(COURSE_ID.getValue(), courseId);
        log.info(FROM_DATE.getValue(), fromDate);
        log.info(TO_DATE.getValue(), toDate);
        return attendanceService.getAttendanceByCourse(courseId, fromDate, toDate);
    }

}
