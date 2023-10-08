package com.university.studentservice.service;

import com.university.studentservice.client.CourseServiceClient;
import com.university.studentservice.exception.BadRequestException;
import com.university.studentservice.exception.NotFoundException;
import com.university.studentservice.model.StudentProfile;
import com.university.studentservice.repository.StudentProfileRepository;
import com.university.studentservice.request.CourseRequest;
import com.university.studentservice.request.StudentProfileRequest;
import com.university.studentservice.response.CourseResponse;
import com.university.studentservice.response.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.university.studentservice.enums.Constants.*;

@Service
@Slf4j
public class StudentProfileService {

    @Autowired
    StudentProfileRepository studentProfileRepository;

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    StudentService studentService;

    @Autowired
    CourseServiceClient courseServiceClient;

    @Autowired
    MongoTemplate mongoTemplate;

    public StudentProfile createProfile(StudentProfileRequest studentProfileRequest) {
        UserInfo userInfo = studentService.getStudentById(studentProfileRequest.getUserId());
        if (!userInfo.getUserType().equals(STUDENT.getValue())) {
            throw new BadRequestException(NOT_A_STUDENT.getValue());
        }
        StudentProfile studentProfile = new StudentProfile();
        BeanUtils.copyProperties(studentProfileRequest, studentProfile);
        studentProfile.setStudentId(sequenceGeneratorService.generateSequenceNumber());
        studentProfile.setUserInfo(userInfo);
        studentProfile.setActive(true);
        StudentProfile savedStudentProfile = studentProfileRepository.save(studentProfile);
        log.info(SAVED_STUDENT_PROFILE.getValue(), savedStudentProfile);
        return savedStudentProfile;
    }

    public List<StudentProfile> getProfiles(Integer pageNo, Integer pageSize) {
        Pageable pageable;
        if (pageNo == null || pageSize == null) {
            pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(pageNo, pageSize);
        }
        Page<StudentProfile> studentProfileList = studentProfileRepository.findAll(pageable);
        log.info(STUDENT_PROFILE.getValue(), studentProfileList);
        return studentProfileList.getContent();
    }

    public StudentProfile getProfile(String studentId) {
        StudentProfile studentProfile = studentProfileRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException(STUDENT_PROFILE_NOT_FOUND.getValue()));
        log.info(STUDENT_PROFILE.getValue(), studentProfile);
        return studentProfile;
    }

    public StudentProfile updateProfile(String studentId, StudentProfileRequest studentProfileRequest) {
        StudentProfile studentProfile = studentProfileRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException(STUDENT_PROFILE_NOT_FOUND.getValue()));
        BeanUtils.copyProperties(studentProfileRequest, studentProfile);
        studentProfile.setUserInfo(studentProfile.getUserInfo());
        StudentProfile updatedStudentProfile = studentProfileRepository.save(studentProfile);
        log.info(UPDATED_STUDENT_PROFILE.getValue(), updatedStudentProfile);
        return updatedStudentProfile;
    }

    public void deleteProfile(String studentId) {
        StudentProfile studentProfile = studentProfileRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException(STUDENT_PROFILE_NOT_FOUND.getValue()));
        studentProfile.setActive(false);
        studentProfileRepository.save(studentProfile);
    }

    public void activateStudent(String studentId) {
        StudentProfile studentProfile = studentProfileRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException(STUDENT_PROFILE_NOT_FOUND.getValue()));
        studentProfile.setActive(true);
        studentProfileRepository.save(studentProfile);
    }

    public Map<String, Set<String>> enrollCourse(String studentId, CourseRequest courseRequest) {
        boolean isEmpty = studentProfileRepository.findById(studentId).isEmpty();
        if (isEmpty) {
            log.error(STUDENT_PROFILE_NOT_FOUND.getValue());
            throw new NotFoundException(STUDENT_PROFILE_NOT_FOUND.getValue());
        }
        List<CourseResponse> availableCourses = courseServiceClient.getCourses();
        Set<String> availableCoursesName = availableCourses.stream().map(CourseResponse::getCourseName).collect(Collectors.toSet());
        log.info(AVAILABLE_COURSES.getValue(), availableCoursesName);
        if (!availableCoursesName.containsAll(courseRequest.getCourses())) {
            throw new NotFoundException(COURSE_NOT_FOUND.getValue());
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(studentId));
        Update update = new Update();
        update.addToSet("coursesEnrolled").each(courseRequest.getCourses());
        FindAndModifyOptions findAndModifyOptions = FindAndModifyOptions.options().returnNew(true);
        StudentProfile studentProfile = mongoTemplate.findAndModify(query, update, findAndModifyOptions, StudentProfile.class, "student_profile");
        Map<String, Set<String>> totalCourseList = new HashMap<>();
        if (studentProfile != null) {
            totalCourseList = Map.of(LIST_AFTER_ENROLLING_COURSE.getValue(), studentProfile.getCoursesEnrolled());
        }
        log.info(TOTAL_COURSE_LIST.getValue(), totalCourseList);
        return totalCourseList;
    }

    public Map<String, Set<String>> removeCourse(String studentId, CourseRequest courseRequest) {
        StudentProfile studentProfile = studentProfileRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException(STUDENT_PROFILE_NOT_FOUND.getValue()));
        Set<String> coursesEnrolled = studentProfile.getCoursesEnrolled();
        log.info(COURSE_ENROLLED.getValue(), coursesEnrolled);
        if (!coursesEnrolled.containsAll(courseRequest.getCourses())) {
            throw new NotFoundException(COURSE_NOT_ENROLLED.getValue());
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(studentId));
        Update update = new Update();
        StudentProfile courseRemoved = new StudentProfile();
        for (String course : courseRequest.getCourses()) {
            update.pull("coursesEnrolled", course);
            FindAndModifyOptions findAndModifyOptions = FindAndModifyOptions.options().returnNew(true);
            courseRemoved = mongoTemplate.findAndModify(query, update, findAndModifyOptions, StudentProfile.class, "student_profile");
        }
        Map<String, Set<String>> totalCourseList = new HashMap<>();
        if (courseRemoved != null) {
            totalCourseList = Map.of(LIST_AFTER_REMOVING_COURSE.getValue(), courseRemoved.getCoursesEnrolled());
        }
        log.info(TOTAL_COURSE_LIST.getValue(), totalCourseList);
        return totalCourseList;
    }

}
