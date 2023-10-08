package com.university.gradeservice.controller;

import com.university.gradeservice.request.GradeRequest;
import com.university.gradeservice.response.GradeResponse;
import com.university.gradeservice.response.Response;
import com.university.gradeservice.service.GradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GradeControllerTest {

    @Mock
    private GradeService gradeService;

    @InjectMocks
    private GradeController gradeController;

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

        GradeResponse gradeResponse = new GradeResponse();
        gradeResponse.setGradeId(1);
        gradeResponse.setMinMarks(70);
        gradeResponse.setMaxMarks(79);
        gradeResponse.setGrade("C");
        when(gradeService.createGrade(gradeRequest)).thenReturn(gradeResponse);

        ResponseEntity<Response> result = gradeController.createGrade(gradeRequest);

        verify(gradeService, times(1)).createGrade(gradeRequest);
        assertNotNull(result);
    }

    @Test
    void testGetGrades() {
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

        List<GradeResponse> gradeResponses = List.of(gradeResponse1, gradeResponse2);
        when(gradeService.getGrades()).thenReturn(gradeResponses);

        List<GradeResponse> result = gradeController.getGrades();

        verify(gradeService, times(1)).getGrades();
        assertNotNull(result);
    }

    @Test
    void testGetGradeById() {
        Integer gradeId = 1;
        GradeResponse gradeResponse = new GradeResponse();
        gradeResponse.setGradeId(1);
        gradeResponse.setMinMarks(70);
        gradeResponse.setMaxMarks(79);
        gradeResponse.setGrade("C");
        when(gradeService.getGradeById(gradeId)).thenReturn(gradeResponse);

        GradeResponse result = gradeController.getGradeById(gradeId);

        verify(gradeService, times(1)).getGradeById(gradeId);
        assertNotNull(result);
    }

    @Test
    void testUpdateGradeById() {
        Integer gradeId = 1;
        GradeRequest gradeRequest = new GradeRequest();
        gradeRequest.setMinMarks(70);
        gradeRequest.setMaxMarks(79);
        gradeRequest.setGrade("C");

        GradeResponse gradeResponse = new GradeResponse();
        gradeResponse.setGradeId(1);
        gradeResponse.setMinMarks(71);
        gradeResponse.setMaxMarks(79);
        gradeResponse.setGrade("C");
        when(gradeService.updateGradeById(gradeId, gradeRequest)).thenReturn(gradeResponse);

        ResponseEntity<Response> result = gradeController.updateGradeById(gradeId, gradeRequest);

        verify(gradeService, times(1)).updateGradeById(gradeId, gradeRequest);
        assertEquals(71, gradeResponse.getMinMarks());
        assertNotNull(result);
    }

    @Test
    void testDeleteGradeById() {
        Integer gradeId = 1;
        HttpStatus expectedStatus = HttpStatus.NO_CONTENT;

        ResponseEntity<HttpStatus> result = gradeController.deleteGradeById(gradeId);

        verify(gradeService, times(1)).deleteGradeById(gradeId);
        assertEquals(expectedStatus, result.getStatusCode());
    }

}