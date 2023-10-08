package com.university.teacherservice.service;

import com.university.teacherservice.client.CourseServiceClient;
import com.university.teacherservice.model.TeacherProfile;
import com.university.teacherservice.repository.TeacherProfileRepository;
import com.university.teacherservice.request.CourseRequest;
import com.university.teacherservice.request.TeacherProfileRequest;
import com.university.teacherservice.response.CourseResponse;
import com.university.teacherservice.response.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static com.university.teacherservice.enums.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeacherProfileServiceTest {

    @Mock
    private TeacherProfileRepository teacherProfileRepository;

    @Mock
    private SequenceGeneratorService sequenceGeneratorService;

    @Mock
    private TeacherService teacherService;

    @Mock
    private CourseServiceClient courseServiceClient;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private TeacherProfileService teacherProfileService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProfile() {
        TeacherProfileRequest teacherProfileRequest = new TeacherProfileRequest();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserType(TEACHER.getValue());
        when(teacherService.getTeacherById(teacherProfileRequest.getUserId())).thenReturn(userInfo);
        when(sequenceGeneratorService.generateSequenceNumber()).thenReturn("HT1");

        TeacherProfile teacherProfile = new TeacherProfile();

        when(teacherProfileRepository.save(any(TeacherProfile.class))).thenReturn(teacherProfile);


        TeacherProfile result = teacherProfileService.createProfile(teacherProfileRequest);

        verify(teacherProfileRepository, times(1)).save(any(TeacherProfile.class));
        assertNotNull(result);
    }

    @Test
    void testGetProfiles() {
        Integer pageNo = 1;
        Integer pageSize = 10;
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNo);
        Page<TeacherProfile> teacherProfiles = mock(Page.class);
        when(teacherProfileRepository.findAll(pageable)).thenReturn(teacherProfiles);

        List<TeacherProfile> result = teacherProfileService.getProfiles(pageNo, pageSize);

        verify(teacherProfileRepository, times(1)).findAll(pageable);
        assertNotNull(result);
    }

    @Test
    void testGetProfile() {
        String teacherId = "HT1";
        TeacherProfile teacherProfile = new TeacherProfile();
        when(teacherProfileRepository.findById(teacherId)).thenReturn(Optional.of(teacherProfile));

        TeacherProfile result = teacherProfileService.getProfile(teacherId);

        verify(teacherProfileRepository, times(1)).findById(teacherId);
        assertNotNull(result);
    }

    @Test
    void testUpdateProfile() {
        String teacherId = "HT1";
        TeacherProfileRequest teacherProfileRequest = new TeacherProfileRequest();
        teacherProfileRequest.setUserId(1);
        TeacherProfile teacherProfile = new TeacherProfile();
        teacherProfile.setTeacherId(teacherId);
        when(teacherProfileRepository.findById(teacherId)).thenReturn(Optional.of(teacherProfile));
        when(teacherProfileRepository.save(teacherProfile)).thenReturn(teacherProfile);

        TeacherProfile result = teacherProfileService.updateProfile(teacherId, teacherProfileRequest);

        verify(teacherProfileRepository, times(1)).save(teacherProfile);
        assertNotNull(result);
    }

    @Test
    void testDeleteProfile() {
        String teacherId = "HT1";
        TeacherProfile teacherProfile = new TeacherProfile();
        when(teacherProfileRepository.findById(teacherId)).thenReturn(Optional.of(teacherProfile));

        teacherProfileService.deleteProfile(teacherId);

        verify(teacherProfileRepository, times(1)).save(teacherProfile);
        assertFalse(teacherProfile.isActive());
    }

    @Test
    void testActivateTeacher() {
        String teacherId = "HT1";
        TeacherProfile teacherProfile = new TeacherProfile();
        when(teacherProfileRepository.findById(teacherId)).thenReturn(Optional.of(teacherProfile));

        teacherProfileService.activateTeacher(teacherId);

        verify(teacherProfileRepository, times(1)).save(teacherProfile);
        assertTrue(teacherProfile.isActive());
    }

    @Test
    void testAssignCourse() {
        String teacherId = "HT1";
        CourseRequest courseRequest = new CourseRequest();
        TeacherProfile teacherProfile = new TeacherProfile();
        when(teacherProfileRepository.findById(teacherId)).thenReturn(Optional.of(teacherProfile));

        List<CourseResponse> availableCourses = Collections.emptyList();
        Set<String> availableCoursesName = availableCourses.stream().map(CourseResponse::getCourseName).collect(Collectors.toSet());
        courseRequest.setCourses(availableCoursesName);
        when(courseServiceClient.getCourses()).thenReturn(availableCourses);

        Map<String, Set<String>> result = teacherProfileService.assignCourse(teacherId, courseRequest);

        verify(teacherProfileRepository, times(1)).findById(teacherId);
        verify(courseServiceClient, times(1)).getCourses();
        assertNotNull(result);
    }

    @Test
    void testRemoveCourse() {
        String teacherId = "HT1";
        CourseRequest courseRequest = new CourseRequest();
        courseRequest.setCourses(Collections.singleton("Core Java"));
        TeacherProfile teacherProfile = new TeacherProfile();
        when(teacherProfileRepository.findById(teacherId)).thenReturn(Optional.of(teacherProfile));

        Set<String> coursesTaught = new HashSet<>(courseRequest.getCourses());
        teacherProfile.setCoursesTaught(coursesTaught);
        when(courseServiceClient.getCourses()).thenReturn(Collections.emptyList());

        Map<String, Set<String>> result = teacherProfileService.removeCourse(teacherId, courseRequest);

        verify(teacherProfileRepository, times(1)).findById(teacherId);
        assertNotNull(result);
    }

}