package com.university.gradeservice.controller;

import com.university.gradeservice.request.GradeRequest;
import com.university.gradeservice.response.GradeResponse;
import com.university.gradeservice.response.Response;
import com.university.gradeservice.service.GradeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.university.gradeservice.enums.Constants.*;

@RestController
@RequestMapping("api/university/grade")
@Slf4j
public class GradeController {

    @Autowired
    GradeService gradeService;

    @PostMapping
    public ResponseEntity<Response> createGrade(@Valid @RequestBody GradeRequest gradeRequest) {
        log.info(GRADE_REQUEST.getValue(), gradeRequest);
        GradeResponse gradeResponse = gradeService.createGrade(gradeRequest);
        return new ResponseEntity<>(
                new Response(HttpStatus.CREATED, GRADED_ADDED.getValue(), gradeResponse), HttpStatus.CREATED);
    }

    @GetMapping
    public List<GradeResponse> getGrades() {
        return gradeService.getGrades();
    }

    @GetMapping("{gradeId}")
    public GradeResponse getGradeById(@PathVariable Integer gradeId) {
        log.info(GRADE_ID.getValue(), gradeId);
        return gradeService.getGradeById(gradeId);
    }

    @PutMapping("{gradeId}")
    public ResponseEntity<Response> updateGradeById(@PathVariable Integer gradeId,
                                                @Valid @RequestBody GradeRequest gradeRequest) {
        log.info(GRADE_ID.getValue(), gradeId);
        log.info(GRADE_REQUEST.getValue(), gradeRequest);
        GradeResponse gradeResponse = gradeService.updateGradeById(gradeId, gradeRequest);
        return new ResponseEntity<>(
                new Response(HttpStatus.OK, GRADED_UPDATED.getValue(), gradeResponse), HttpStatus.OK);
    }

    @DeleteMapping("{gradeId}")
    public ResponseEntity<HttpStatus> deleteGradeById(@PathVariable Integer gradeId) {
        log.info(GRADE_ID.getValue(), gradeId);
        gradeService.deleteGradeById(gradeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
