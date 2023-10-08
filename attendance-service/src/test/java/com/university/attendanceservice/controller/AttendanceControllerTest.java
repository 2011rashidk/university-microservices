package com.university.attendanceservice.controller;

import com.university.attendanceservice.request.AttendanceRequest;
import com.university.attendanceservice.response.AttendanceResponse;
import com.university.attendanceservice.response.CourseAttendanceDetails;
import com.university.attendanceservice.response.Response;
import com.university.attendanceservice.service.AttendanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AttendanceControllerTest {

    @Mock
    private AttendanceService attendanceService;

    @InjectMocks
    private AttendanceController attendanceController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPostAttendance() {
        AttendanceRequest attendanceRequest = new AttendanceRequest();
        AttendanceResponse attendanceResponse = new AttendanceResponse();
        when(attendanceService.postAttendance(attendanceRequest)).thenReturn(attendanceResponse);

        ResponseEntity<Response> result = attendanceController.postAttendance(attendanceRequest);

        verify(attendanceService, times(1)).postAttendance(attendanceRequest);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals("Attendance posted successfully!", result.getBody().getMessage());
        assertEquals(attendanceResponse, result.getBody().getData());
    }

    @Test
    void testGetAttendance() {
        Integer attendanceId = 1;
        AttendanceResponse attendanceResponse = new AttendanceResponse();
        when(attendanceService.getAttendance(attendanceId)).thenReturn(attendanceResponse);

        AttendanceResponse result = attendanceController.getAttendance(attendanceId);

        verify(attendanceService, times(1)).getAttendance(attendanceId);
        assertEquals(attendanceResponse, result);
    }

    @Test
    void testGetAttendanceByCourse() throws ParseException {
        Integer courseId = 1;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate = format.parse("2023-08-06");
        Date toDate = format.parse("2023-08-09");
        CourseAttendanceDetails courseAttendanceDetails = new CourseAttendanceDetails();
        when(attendanceService.getAttendanceByCourse(courseId, fromDate, toDate)).thenReturn(courseAttendanceDetails);

        CourseAttendanceDetails result = attendanceController.getAttendanceByCourse(courseId, fromDate, toDate);

        verify(attendanceService, times(1)).getAttendanceByCourse(courseId, fromDate, toDate);
        assertEquals(courseAttendanceDetails, result);
    }

}