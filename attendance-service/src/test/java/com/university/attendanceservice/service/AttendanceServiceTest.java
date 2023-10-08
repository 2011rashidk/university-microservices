package com.university.attendanceservice.service;

import com.university.attendanceservice.client.CourseServiceClient;
import com.university.attendanceservice.client.StudentServiceClient;
import com.university.attendanceservice.entity.Attendance;
import com.university.attendanceservice.repository.AttendanceRepository;
import com.university.attendanceservice.request.AttendanceRequest;
import com.university.attendanceservice.response.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AttendanceServiceTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private CourseServiceClient courseServiceClient;

    @Mock
    private StudentServiceClient studentServiceClient;

    @InjectMocks
    private AttendanceService attendanceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPostAttendance() {
        AttendanceRequest attendanceRequest = new AttendanceRequest();
        attendanceRequest.setCourseId(1);
        StudentProfile studentProfile = new StudentProfile();
        studentProfile.setCoursesEnrolled(Set.of("Core Java"));
        when(studentServiceClient.getProfile(attendanceRequest.getStudentId())).thenReturn(studentProfile);
        CourseResponse course = new CourseResponse();
        course.setCourseId(1);
        course.setCourseName("Core Java");
        when(courseServiceClient.getCourseById(attendanceRequest.getCourseId())).thenReturn(course);
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(new Attendance());

        AttendanceResponse result = attendanceService.postAttendance(attendanceRequest);

        assertNotNull(result);
        assertEquals(AttendanceResponse.class, result.getClass());
    }

    @Test
    void testGetAttendance() {
        Integer attendanceId = 1;
        Attendance attendance = new Attendance();
        when(attendanceRepository.findById(attendanceId)).thenReturn(Optional.of(attendance));

        AttendanceResponse result = attendanceService.getAttendance(attendanceId);

        assertNotNull(result);
        assertEquals(AttendanceResponse.class, result.getClass());
    }

    @Test
    void testGetAttendanceByCourse() throws ParseException {
        Integer courseId = 1;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate = format.parse("2023-08-06");
        Date toDate = format.parse("2023-08-09");
        List<Attendance> attendanceList = new ArrayList<>();

        CourseResponse course = new CourseResponse();
        course.setCourseName("Software Testing");

        when(attendanceRepository.getAttendanceByCourseWithDate(courseId, fromDate, toDate)).thenReturn(attendanceList);
        when(courseServiceClient.getCourseById(courseId)).thenReturn(course);

        CourseAttendanceDetails result = attendanceService.getAttendanceByCourse(courseId, fromDate, toDate);

        assertNotNull(result);
        assertEquals(CourseAttendanceDetails.class, result.getClass());
        assertEquals("Software Testing", result.getCourseName());
    }

    @Test
    void testGetAttendanceByCourseForNoDate() {
        Integer courseId = 1;
        List<Attendance> attendanceList = new ArrayList<>();

        CourseResponse course = new CourseResponse();
        course.setCourseName("Software Testing");

        when(attendanceRepository.findByCourseId(courseId)).thenReturn(attendanceList);
        when(courseServiceClient.getCourseById(courseId)).thenReturn(course);

        CourseAttendanceDetails result = attendanceService.getAttendanceByCourse(courseId, null, null);

        assertNotNull(result);
        assertEquals(CourseAttendanceDetails.class, result.getClass());
        assertEquals("Software Testing", result.getCourseName());
    }

}