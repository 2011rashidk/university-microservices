package com.university.gradeservice.service;

import com.university.gradeservice.entity.Grade;
import com.university.gradeservice.repository.GradeRepository;
import com.university.gradeservice.request.GradeRequest;
import com.university.gradeservice.response.GradeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GradeServiceTest {

    @Mock
    private GradeRepository gradeRepository;

    @InjectMocks
    private GradeService gradeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateGrade() {
        GradeRequest gradeRequest = new GradeRequest();
        gradeRequest.setMinMarks(70);
        gradeRequest.setMaxMarks(79);
        gradeRequest.setGrade("C");

        Grade grade = new Grade();

        GradeResponse expectedResponse = new GradeResponse();
        expectedResponse.setGradeId(1);
        expectedResponse.setMinMarks(70);
        expectedResponse.setMaxMarks(79);
        expectedResponse.setGrade("C");
        when(gradeRepository.save(any(Grade.class))).thenReturn(grade);

        GradeResponse result = gradeService.createGrade(gradeRequest);

        verify(gradeRepository, times(1)).save(any(Grade.class));
        assertNotNull(result);
    }

    @Test
    void testGetGrades() {
        Grade grade1 = new Grade();
        grade1.setGradeId(1);
        grade1.setMinMarks(90);
        grade1.setMaxMarks(100);
        grade1.setGrade("A");

        Grade grade2 = new Grade();
        grade2.setGradeId(2);
        grade2.setMinMarks(80);
        grade2.setMaxMarks(89);
        grade2.setGrade("B");

        List<Grade> gradeList = List.of(grade1, grade2);

        GradeResponse gradeResponse1 = new GradeResponse();
        gradeResponse1.setGradeId(1);
        gradeResponse1.setMinMarks(90);
        gradeResponse1.setMaxMarks(100);
        gradeResponse1.setGrade("A");

        GradeResponse gradeResponse2 = new GradeResponse();
        gradeResponse2.setGradeId(2);
        gradeResponse2.setMinMarks(80);
        gradeResponse2.setMaxMarks(89);
        gradeResponse2.setGrade("B");

        List<GradeResponse> expectedResponseList = List.of(gradeResponse1, gradeResponse2);
        when(gradeRepository.findAll()).thenReturn(gradeList);

        List<GradeResponse> result = gradeService.getGrades();

        verify(gradeRepository, times(1)).findAll();
        assertEquals(expectedResponseList, result);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testGetGradeById() {
        Integer gradeId = 1;
        Grade grade = new Grade();
        grade.setGradeId(1);
        grade.setMinMarks(90);
        grade.setMaxMarks(100);
        grade.setGrade("A");
        when(gradeRepository.findById(gradeId)).thenReturn(Optional.of(grade));

        GradeResponse result = gradeService.getGradeById(gradeId);

        verify(gradeRepository, times(1)).findById(gradeId);
        assertNotNull(result);
    }

    @Test
    void testUpdateGradeById() {
        Integer gradeId = 1;
        GradeRequest gradeRequest = new GradeRequest();
        gradeRequest.setMinMarks(70);
        gradeRequest.setMaxMarks(79);
        gradeRequest.setGrade("C");

        Grade grade = new Grade();
        grade.setGradeId(1);
        grade.setMinMarks(70);
        grade.setMaxMarks(79);
        grade.setGrade("C");

        when(gradeRepository.findById(gradeId)).thenReturn(Optional.of(grade));
        when(gradeRepository.save(any(Grade.class))).thenReturn(grade);

        GradeResponse result = gradeService.updateGradeById(gradeId, gradeRequest);

        verify(gradeRepository, times(1)).findById(gradeId);
        verify(gradeRepository, times(1)).save(any(Grade.class));
        assertNotNull(result);
    }

    @Test
    void testDeleteGradeById() {
        Integer gradeId = 1;
        when(gradeRepository.findById(gradeId)).thenReturn(Optional.of(new Grade()));

        assertDoesNotThrow(() -> gradeService.deleteGradeById(gradeId));

        verify(gradeRepository, times(1)).findById(gradeId);
        verify(gradeRepository, times(1)).deleteById(gradeId);
    }

    @Test
    void testGetGradeByMarks() {
        Integer scoredMarks = 90;
        Grade grade = new Grade();
        when(gradeRepository.getGradeByMarks(scoredMarks)).thenReturn(grade);

        Grade result = gradeService.getGradeByMarks(scoredMarks);

        verify(gradeRepository, times(1)).getGradeByMarks(scoredMarks);
        assertNotNull(result);
    }

}