package com.university.studentservice.service;

import com.university.studentservice.client.CourseServiceClient;
import com.university.studentservice.model.StudentProfile;
import com.university.studentservice.repository.StudentProfileRepository;
import com.university.studentservice.request.CourseRequest;
import com.university.studentservice.request.StudentProfileRequest;
import com.university.studentservice.response.CourseResponse;
import com.university.studentservice.response.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.*;

import static com.university.studentservice.enums.Constants.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class StudentProfileServiceTest {

    @Mock
    private StudentProfileRepository studentProfileRepository;

    @Mock
    private SequenceGeneratorService sequenceGeneratorService;

    @Mock
    private StudentService studentService;

    @Mock
    private CourseServiceClient courseServiceClient;

    @InjectMocks
    private StudentProfileService studentProfileService;

    @Mock
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProfile() {
        StudentProfileRequest studentProfileRequest = new StudentProfileRequest();
        studentProfileRequest.setUserId(1);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserType(STUDENT.getValue());
        when(studentService.getStudentById(studentProfileRequest.getUserId())).thenReturn(userInfo);
        when(sequenceGeneratorService.generateSequenceNumber()).thenReturn("HS2");

        StudentProfile expectedStudentProfile = new StudentProfile();

        when(studentProfileRepository.save(any(StudentProfile.class))).thenReturn(expectedStudentProfile);

        StudentProfile result = studentProfileService.createProfile(studentProfileRequest);

        verify(studentProfileRepository, times(1)).save(any(StudentProfile.class));
        assertNotNull(result);
        assertEquals(expectedStudentProfile, result);
    }

    @Test
    void testGetProfiles() {
        Integer pageNo = 1;
        Integer pageSize = 5;
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNo);
        Page<StudentProfile> studentProfiles = mock(Page.class);
        when(studentProfileRepository.findAll(pageable)).thenReturn(studentProfiles);

        List<StudentProfile> result = studentProfileService.getProfiles(pageNo, pageSize);

        verify(studentProfileRepository, times(1)).findAll(pageable);
        assertNotNull(result);
    }

    @Test
    void testGetProfile() {
        String studentId = "HS1";
        StudentProfile studentProfile = new StudentProfile();
        studentProfile.setStudentId(studentId);
        when(studentProfileRepository.findById(studentId)).thenReturn(Optional.of(studentProfile));

        StudentProfile result = studentProfileService.getProfile(studentId);

        verify(studentProfileRepository, times(1)).findById(studentId);
        assertNotNull(result);
    }

    @Test
    void testUpdateProfile() {
        String studentId = "HS1";
        StudentProfileRequest studentProfileRequest = new StudentProfileRequest();
        studentProfileRequest.setUserId(1);
        StudentProfile studentProfile = new StudentProfile();
        studentProfile.setStudentId(studentId);

        when(studentProfileRepository.findById(studentId)).thenReturn(Optional.of(studentProfile));

        when(studentProfileRepository.save(studentProfile)).thenReturn(studentProfile);

        StudentProfile result = studentProfileService.updateProfile(studentId, studentProfileRequest);

        verify(studentProfileRepository, times(1)).save(studentProfile);
        assertNotNull(result);
    }

    @Test
    void testDeleteProfile() {
        String studentId = "HS1";
        StudentProfile studentProfile = new StudentProfile();
        studentProfile.setStudentId(studentId);
        when(studentProfileRepository.findById(studentId)).thenReturn(Optional.of(studentProfile));

        studentProfileService.deleteProfile(studentId);

        verify(studentProfileRepository, times(1)).save(studentProfile);
        assertFalse(studentProfile.isActive());
    }

    @Test
    void testActivateStudent() {
        String studentId = "HS1";
        StudentProfile studentProfile = new StudentProfile();
        studentProfile.setStudentId(studentId);
        when(studentProfileRepository.findById(studentId)).thenReturn(Optional.of(studentProfile));

        studentProfileService.activateStudent(studentId);

        verify(studentProfileRepository, times(1)).save(studentProfile);
        assertTrue(studentProfile.isActive());
    }

    @Test
    void testEnrollCourse() {
        String studentId = "HS1";
        CourseRequest courseRequest = new CourseRequest();
        courseRequest.setCourses(Collections.emptySet());
        StudentProfile studentProfile = new StudentProfile();
        studentProfile.setStudentId(studentId);
        when(studentProfileRepository.findById(studentId)).thenReturn(Optional.of(studentProfile));
        List<CourseResponse> availableCourses = Collections.emptyList();
        when(courseServiceClient.getCourses()).thenReturn(availableCourses);

        Map<String, Set<String>> result = studentProfileService.enrollCourse(studentId, courseRequest);

        verify(studentProfileRepository, times(1)).findById(studentId);
        verify(courseServiceClient, times(1)).getCourses();
        assertNotNull(result);
    }

    @Test
    void testRemoveCourse() {
        String studentId = "HS1";
        CourseRequest courseRequest = new CourseRequest();
        courseRequest.setCourses(Collections.singleton("Core Java"));
        StudentProfile studentProfile = new StudentProfile();
        studentProfile.setStudentId(studentId);
        Set<String> coursesEnrolled = new HashSet<>(courseRequest.getCourses());
        studentProfile.setCoursesEnrolled(coursesEnrolled);
        when(studentProfileRepository.findById(studentId)).thenReturn(Optional.of(studentProfile));
        when(courseServiceClient.getCourses()).thenReturn(Collections.emptyList());

        Map<String, Set<String>> result = studentProfileService.removeCourse(studentId, courseRequest);

        verify(studentProfileRepository, times(1)).findById(studentId);
        assertNotNull(result);
    }
}