package com.university.studentservice.service;

import com.university.studentservice.exception.NotFoundException;
import com.university.studentservice.model.StudentProfile;
import com.university.studentservice.repository.StudentProfileRepository;
import com.university.studentservice.request.StudentProfileRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.university.studentservice.enums.Constants.*;

@Service
@Slf4j
public class StudentProfileService {

    @Autowired
    StudentProfileRepository studentProfileRepository;

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    public StudentProfile createProfile(StudentProfileRequest studentProfileRequest) {
        StudentProfile studentProfile = new StudentProfile();
        BeanUtils.copyProperties(studentProfileRequest, studentProfile);
        studentProfile.setStudentId(sequenceGeneratorService.generateSequenceNumber());
        studentProfile.setActive(true);
        StudentProfile savedStudentProfile = studentProfileRepository.save(studentProfile);
        log.info(SAVED_STUDENT_PROFILE.getValue(), savedStudentProfile);
        return savedStudentProfile;
    }

    public List<StudentProfile> getProfiles() {
        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        log.info(STUDENT_PROFILE.getValue(), studentProfileList);
        return studentProfileList;
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

}
