package com.university.studentservice.controller;

import com.university.studentservice.model.StudentProfile;
import com.university.studentservice.request.CourseRequest;
import com.university.studentservice.request.StudentProfileRequest;
import com.university.studentservice.response.Response;
import com.university.studentservice.service.StudentProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static com.university.studentservice.enums.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StudentProfileControllerTest {

    @Mock
    private StudentProfileService studentProfileService;

    @InjectMocks
    private StudentProfileController studentProfileController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProfile() {
        StudentProfileRequest studentProfileRequest = new StudentProfileRequest();
        StudentProfile studentProfile = new StudentProfile();
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(
                new Response(HttpStatus.CREATED, STUDENT_PROFILE_CREATED.getValue(), studentProfile), HttpStatus.CREATED);

        when(studentProfileService.createProfile(studentProfileRequest)).thenReturn(studentProfile);
        ResponseEntity<Response> result = studentProfileController.createProfile(studentProfileRequest);
        verify(studentProfileService).createProfile(studentProfileRequest);

        assertEquals(responseEntity, result);
    }

    @Test
    void testGetProfiles() {
        List<StudentProfile> studentProfiles = Collections.singletonList(new StudentProfile());

        ResponseEntity<Response> responseEntity = new ResponseEntity<>(
                new Response(HttpStatus.OK, STUDENT_PROFILE_RETRIEVED.getValue(), studentProfiles), HttpStatus.OK);

        when(studentProfileService.getProfiles(null, null)).thenReturn(studentProfiles);
        ResponseEntity<Response> result = studentProfileController.getProfiles(null, null);
        verify(studentProfileService).getProfiles(null, null);

        assertEquals(responseEntity, result);
    }

    @Test
    void testGetProfile() {
        String studentId = "HS1";
        StudentProfile studentProfile = new StudentProfile();

        when(studentProfileService.getProfile(studentId)).thenReturn(studentProfile);
        StudentProfile result = studentProfileController.getProfile(studentId);
        verify(studentProfileService).getProfile(studentId);

        assertEquals(studentProfile, result);
    }

    @Test
    void testUpdateProfile() {
        String studentId = "HS1";
        StudentProfileRequest studentProfileRequest = new StudentProfileRequest();
        StudentProfile studentProfile = new StudentProfile();
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(
                new Response(HttpStatus.OK, STUDENT_PROFILE_UPDATED.getValue(), studentProfile), HttpStatus.OK);

        when(studentProfileService.updateProfile(studentId, studentProfileRequest)).thenReturn(studentProfile);
        ResponseEntity<Response> result = studentProfileController.updateProfile(studentId, studentProfileRequest);
        verify(studentProfileService).updateProfile(studentId, studentProfileRequest);

        assertEquals(responseEntity, result);
    }

    @Test
    void testDeleteProfile() {
        String studentId = "HS1";
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(
                new Response(HttpStatus.NO_CONTENT, STUDENT_PROFILE_DELETED.getValue(), null), HttpStatus.OK);

        ResponseEntity<Response> result = studentProfileController.deleteProfile(studentId);
        verify(studentProfileService).deleteProfile(studentId);

        assertEquals(responseEntity, result);
    }

    @Test
    void testActivateStudent() {
        String studentId = "HS1";
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(
                new Response(HttpStatus.OK, STUDENT_ACTIVATED.getValue(), null), HttpStatus.OK);

        ResponseEntity<Response> result = studentProfileController.activateStudent(studentId);
        verify(studentProfileService).activateStudent(studentId);

        assertEquals(responseEntity, result);
    }

    @Test
    void testEnrollCourse() {
        String studentId = "HS1";
        CourseRequest courseRequest = new CourseRequest();
        Map<String, Set<String>> assignedCourse = new HashMap<>();
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(
                new Response(HttpStatus.OK, COURSES_ENROLLED.getValue(), assignedCourse), HttpStatus.OK);

        when(studentProfileService.enrollCourse(studentId, courseRequest)).thenReturn(assignedCourse);
        ResponseEntity<Response> result = studentProfileController.enrollCourse(studentId, courseRequest);
        verify(studentProfileService).enrollCourse(studentId, courseRequest);

        assertEquals(responseEntity, result);
    }

    @Test
    void testRemoveCourse() {
        String studentId = "HS1";
        CourseRequest courseRequest = new CourseRequest();
        Map<String, Set<String>> removedCourse = new HashMap<>();
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(
                new Response(HttpStatus.OK, COURSE_REMOVED.getValue(), removedCourse), HttpStatus.OK);

        when(studentProfileService.removeCourse(studentId, courseRequest)).thenReturn(removedCourse);
        ResponseEntity<Response> result = studentProfileController.removeCourse(studentId, courseRequest);
        verify(studentProfileService).removeCourse(studentId, courseRequest);

        assertEquals(responseEntity, result);
    }

}