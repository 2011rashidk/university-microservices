package com.university.gradeservice.service;

import com.university.gradeservice.entity.Grade;
import com.university.gradeservice.exception.NotFoundException;
import com.university.gradeservice.repository.GradeRepository;
import com.university.gradeservice.request.GradeRequest;
import com.university.gradeservice.response.GradeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.university.gradeservice.enums.Constants.*;

@Service
@Slf4j
public class GradeService {

    @Autowired
    GradeRepository gradeRepository;

    public GradeResponse createGrade(GradeRequest gradeRequest) {
        Grade grade = new Grade();
        BeanUtils.copyProperties(gradeRequest, grade);
        Grade savedGrade = gradeRepository.save(grade);
        GradeResponse gradeResponse = new GradeResponse();
        BeanUtils.copyProperties(savedGrade, gradeResponse);
        log.info(GRADE_RESPONSE.getValue(), gradeResponse);
        return gradeResponse;
    }

    public List<GradeResponse> getGrades() {
        List<GradeResponse> gradeResponseList = new ArrayList<>();
        List<Grade> gradeList = gradeRepository.findAll();
        for (Grade grade : gradeList) {
            GradeResponse gradeResponse = new GradeResponse();
            BeanUtils.copyProperties(grade, gradeResponse);
            gradeResponseList.add(gradeResponse);
        }
        log.info(GRADE_RESPONSE.getValue(), gradeResponseList);
        return gradeResponseList;
    }

    public GradeResponse getGradeById(Integer gradeId) {
        GradeResponse gradeResponse = new GradeResponse();
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new NotFoundException(GRADE_NOT_FOUND.getValue()));
        BeanUtils.copyProperties(grade, gradeResponse);
        log.info(GRADE_RESPONSE.getValue(), gradeResponse);
        return gradeResponse;
    }

    public GradeResponse updateGradeById(Integer gradeId, GradeRequest gradeRequest) {
        boolean isEmpty = gradeRepository.findById(gradeId).isEmpty();
        if (isEmpty) {
            log.error(GRADE_NOT_FOUND.getValue());
            throw new NotFoundException(GRADE_NOT_FOUND.getValue());
        }
        Grade grade = new Grade();
        BeanUtils.copyProperties(gradeRequest, grade);
        grade.setGradeId(gradeId);
        Grade updatedGrade = gradeRepository.save(grade);
        GradeResponse gradeResponse = new GradeResponse();
        BeanUtils.copyProperties(updatedGrade, gradeResponse);
        log.info(GRADE_RESPONSE.getValue(), gradeResponse);
        return gradeResponse;
    }

    public void deleteGradeById(Integer gradeId) {
        boolean isEmpty = gradeRepository.findById(gradeId).isEmpty();
        if (isEmpty) {
            log.error(GRADE_NOT_FOUND.getValue());
            throw new NotFoundException(GRADE_NOT_FOUND.getValue());
        }
        gradeRepository.deleteById(gradeId);
    }

    public Grade getGradeByMarks(Integer scoredMarks) {
        return gradeRepository.getGradeByMarks(scoredMarks);
    }

}
