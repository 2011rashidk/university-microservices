package com.university.teacherservice.controller;

import com.university.teacherservice.model.TeacherProfile;
import com.university.teacherservice.request.CourseRequest;
import com.university.teacherservice.request.TeacherProfileRequest;
import com.university.teacherservice.response.Response;
import com.university.teacherservice.service.TeacherProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.university.teacherservice.enums.Constants.TEACHER_PROFILE_DELETED;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TeacherProfileControllerTest {

    @Mock
    private TeacherProfileService teacherProfileService;

    @InjectMocks
    private TeacherProfileController teacherProfileController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProfile() {
        TeacherProfileRequest teacherProfileRequest = new TeacherProfileRequest();
        TeacherProfile teacherProfile = new TeacherProfile();
        when(teacherProfileService.createProfile(teacherProfileRequest)).thenReturn(teacherProfile);

        ResponseEntity<Response> result = teacherProfileController.createProfile(teacherProfileRequest);

        verify(teacherProfileService, times(1)).createProfile(teacherProfileRequest);
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void testGetProfiles() {
        Integer pageNo = 1;
        Integer pageSize = 5;
        List<TeacherProfile> teacherProfiles = List.of(new TeacherProfile());
        when(teacherProfileService.getProfiles(pageNo, pageSize)).thenReturn(teacherProfiles);

        ResponseEntity<Response> result = teacherProfileController.getProfiles(pageNo, pageSize);

        verify(teacherProfileService, times(1)).getProfiles(pageNo, pageSize);
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testGetProfile() {
        String teacherId = "HT1";
        TeacherProfile teacherProfile = new TeacherProfile();
        when(teacherProfileService.getProfile(teacherId)).thenReturn(teacherProfile);

        ResponseEntity<Response> result = teacherProfileController.getProfile(teacherId);

        verify(teacherProfileService, times(1)).getProfile(teacherId);
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testUpdateProfile() {
        String teacherId = "HT1";
        TeacherProfileRequest teacherProfileRequest = new TeacherProfileRequest();
        TeacherProfile teacherProfile = new TeacherProfile();
        when(teacherProfileService.updateProfile(teacherId, teacherProfileRequest)).thenReturn(teacherProfile);

        ResponseEntity<Response> result = teacherProfileController.updateProfile(teacherId, teacherProfileRequest);

        verify(teacherProfileService, times(1)).updateProfile(teacherId, teacherProfileRequest);
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testDeleteProfile() {
        String teacherId = "HT1";

        ResponseEntity<Response> result = teacherProfileController.deleteProfile(teacherId);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(
                new Response(HttpStatus.NO_CONTENT, TEACHER_PROFILE_DELETED.getValue(), null), HttpStatus.OK);

        verify(teacherProfileService).deleteProfile(teacherId);
        assertNotNull(result);
        assertEquals(responseEntity, result);
    }

    @Test
    void testActivateTeacher() {
        String teacherId = "HT1";

        ResponseEntity<Response> result = teacherProfileController.activateTeacher(teacherId);

        verify(teacherProfileService, times(1)).activateTeacher(teacherId);
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testAssignCourse() {
        String teacherId = "HT1";
        CourseRequest courseRequest = new CourseRequest();
        Map<String, Set<String>> assignedCourse = Map.of();
        when(teacherProfileService.assignCourse(teacherId, courseRequest)).thenReturn(assignedCourse);

        ResponseEntity<Response> result = teacherProfileController.assignCourse(teacherId, courseRequest);

        verify(teacherProfileService, times(1)).assignCourse(teacherId, courseRequest);
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testRemoveCourse() {
        String teacherId = "HT1";
        CourseRequest courseRequest = new CourseRequest();
        Map<String, Set<String>> removedCourse = Map.of();
        when(teacherProfileService.removeCourse(teacherId, courseRequest)).thenReturn(removedCourse);

        ResponseEntity<Response> result = teacherProfileController.removeCourse(teacherId, courseRequest);

        verify(teacherProfileService, times(1)).removeCourse(teacherId, courseRequest);
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}