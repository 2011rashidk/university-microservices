package com.university.teacherservice.service;

import com.university.teacherservice.client.CourseServiceClient;
import com.university.teacherservice.exception.BadRequestException;
import com.university.teacherservice.exception.NotFoundException;
import com.university.teacherservice.model.TeacherProfile;
import com.university.teacherservice.repository.TeacherProfileRepository;
import com.university.teacherservice.request.CourseRequest;
import com.university.teacherservice.request.TeacherProfileRequest;
import com.university.teacherservice.response.CourseResponse;
import com.university.teacherservice.response.UserInfo;
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

import java.util.*;
import java.util.stream.Collectors;

import static com.university.teacherservice.enums.Constants.*;

@Service
@Slf4j
public class TeacherProfileService {

    @Autowired
    TeacherProfileRepository teacherProfileRepository;

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    CourseServiceClient courseServiceClient;

    @Autowired
    MongoTemplate mongoTemplate;

    public TeacherProfile createProfile(TeacherProfileRequest teacherProfileRequest) {
        UserInfo userInfo = teacherService.getTeacherById(teacherProfileRequest.getUserId());
        if (!userInfo.getUserType().equals(TEACHER.getValue())) {
            throw new BadRequestException(NOT_A_TEACHER.getValue());
        }
        TeacherProfile teacherProfile = new TeacherProfile();
        BeanUtils.copyProperties(teacherProfileRequest, teacherProfile);
        teacherProfile.setTeacherId(sequenceGeneratorService.generateSequenceNumber());
        teacherProfile.setUserInfo(userInfo);
        teacherProfile.setActive(true);
        TeacherProfile savedTeacherProfile = teacherProfileRepository.save(teacherProfile);
        log.info(SAVED_TEACHER_PROFILE.getValue(), savedTeacherProfile);
        return savedTeacherProfile;
    }

    public List<TeacherProfile> getProfiles(Integer pageNo, Integer pageSize) {
        Pageable pageable;
        if (pageNo == null || pageSize == null) {
            pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(pageNo, pageSize);
        }
        Page<TeacherProfile> teacherProfileList = teacherProfileRepository.findAll(pageable);
        log.info(TEACHER_PROFILE.getValue(), teacherProfileList);
        return teacherProfileList.getContent();
    }

    public TeacherProfile getProfile(String teacherId) {
        TeacherProfile teacherProfile = teacherProfileRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundException(TEACHER_PROFILE_NOT_FOUND.getValue()));
        log.info(TEACHER_PROFILE.getValue(), teacherProfile);
        return teacherProfile;
    }

    public TeacherProfile updateProfile(String teacherId, TeacherProfileRequest teacherProfileRequest) {
        TeacherProfile teacherProfile = teacherProfileRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundException(TEACHER_PROFILE_NOT_FOUND.getValue()));
        BeanUtils.copyProperties(teacherProfileRequest, teacherProfile);
        teacherProfile.setUserInfo(teacherProfile.getUserInfo());
        TeacherProfile updatedTeacherProfile = teacherProfileRepository.save(teacherProfile);
        log.info(UPDATED_TEACHER_PROFILE.getValue(), updatedTeacherProfile);
        return updatedTeacherProfile;
    }

    public void deleteProfile(String teacherId) {
        TeacherProfile teacherProfile = teacherProfileRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundException(TEACHER_PROFILE_NOT_FOUND.getValue()));
        teacherProfile.setActive(false);
        teacherProfileRepository.save(teacherProfile);
    }

    public void activateTeacher(String teacherId) {
        TeacherProfile teacherProfile = teacherProfileRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundException(TEACHER_PROFILE_NOT_FOUND.getValue()));
        teacherProfile.setActive(true);
        teacherProfileRepository.save(teacherProfile);
    }

    public Map<String, Set<String>> assignCourse(String teacherId, CourseRequest courseRequest) {
        List<CourseResponse> availableCourses = courseServiceClient.getCourses();
        Set<String> availableCoursesName = availableCourses.stream().map(CourseResponse::getCourseName).collect(Collectors.toSet());
        log.info(AVAILABLE_COURSES.getValue(), availableCoursesName);
        if (!availableCoursesName.containsAll(courseRequest.getCourses())) {
            throw new NotFoundException(COURSE_NOT_FOUND.getValue());
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(teacherId));
        Update update = new Update();
        update.addToSet("coursesTaught").each(courseRequest.getCourses());
        FindAndModifyOptions findAndModifyOptions = FindAndModifyOptions.options().returnNew(true);
        TeacherProfile teacherProfile = mongoTemplate.findAndModify(query, update, findAndModifyOptions, TeacherProfile.class, "teacher_profile");
        Map<String, Set<String>> totalCourseList = new HashMap<>();
        if (teacherProfile != null) {
            totalCourseList = Map.of(LIST_AFTER_ADDING_COURSE.getValue(), teacherProfile.getCoursesTaught());
        }
        log.info(TOTAL_COURSE_LIST.getValue(), totalCourseList);
        return totalCourseList;
    }

    public Map<String, Set<String>> removeCourse(String teacherId, CourseRequest courseRequest) {
        TeacherProfile teacherProfile = teacherProfileRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundException(TEACHER_PROFILE_NOT_FOUND.getValue()));
        Set<String> coursesTaught = teacherProfile.getCoursesTaught();
        log.info(COURSES_TAUGHT.getValue(), coursesTaught);
        if (!coursesTaught.containsAll(courseRequest.getCourses())) {
            throw new NotFoundException(COURSE_NOT_TAUGHT.getValue());
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(teacherId));
        Update update = new Update();
        TeacherProfile courseRemoved = new TeacherProfile();
        for (String course : courseRequest.getCourses()) {
            update.pull("coursesTaught", course);
            FindAndModifyOptions findAndModifyOptions = FindAndModifyOptions.options().returnNew(true);
            courseRemoved = mongoTemplate.findAndModify(query, update, findAndModifyOptions, TeacherProfile.class, "teacher_profile");
        }
        Map<String, Set<String>> totalCourseList = new HashMap<>();
        if (courseRemoved != null) {
            totalCourseList = Map.of(LIST_AFTER_REMOVING_COURSE.getValue(), courseRemoved.getCoursesTaught());
        }
        log.info(TOTAL_COURSE_LIST.getValue(), totalCourseList);
        return totalCourseList;
    }

}
