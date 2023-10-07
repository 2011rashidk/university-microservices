package com.university.courseservice.service;

import com.university.courseservice.entity.Course;
import com.university.courseservice.exception.NotFoundException;
import com.university.courseservice.repository.CourseRepository;
import com.university.courseservice.request.CourseRequest;
import com.university.courseservice.response.CourseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.university.courseservice.enums.Constants.*;

@Service
@Slf4j
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    public CourseResponse createCourse(CourseRequest courseRequest) {
        Course course = new Course();
        BeanUtils.copyProperties(courseRequest, course);
        course.setActive(true);
        Course savedCourse = courseRepository.save(course);
        CourseResponse courseResponse = new CourseResponse();
        BeanUtils.copyProperties(savedCourse, courseResponse);
        log.info(COURSE_RESPONSE.getValue(), courseResponse);
        return courseResponse;
    }

    public List<CourseResponse> getCourses() {
        List<CourseResponse> courseResponseList = new ArrayList<>();
        List<Course> courseList = courseRepository.findAll();
        for (Course course : courseList) {
            CourseResponse courseResponse = new CourseResponse();
            BeanUtils.copyProperties(course, courseResponse);
            courseResponseList.add(courseResponse);
        }
        return courseResponseList;
    }

    public CourseResponse getCourseById(Integer courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException(NO_COURSE_FOUND.getValue()));
        CourseResponse courseResponse = new CourseResponse();
        BeanUtils.copyProperties(course, courseResponse);
        log.info(COURSE_RESPONSE.getValue(), courseResponse);
        return courseResponse;
    }

    public CourseResponse updateCourseById(Integer courseId, CourseRequest courseRequest) {
        boolean isEmpty = courseRepository.findById(courseId).isEmpty();
        if (isEmpty) {
            log.error(NO_COURSE_FOUND.getValue());
            throw new NotFoundException(NO_COURSE_FOUND.getValue());
        }
        Course course = new Course();
        BeanUtils.copyProperties(courseRequest, course);
        course.setCourseId(courseId);
        course.setActive(true);
        Course updatedCourse = courseRepository.save(course);
        CourseResponse courseResponse = new CourseResponse();
        BeanUtils.copyProperties(updatedCourse, courseResponse);
        log.info(COURSE_RESPONSE.getValue(), courseResponse);
        return courseResponse;
    }

    public void deleteCourseById(Integer courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException(NO_COURSE_FOUND.getValue()));
        course.setActive(false);
        courseRepository.save(course);
    }

}
