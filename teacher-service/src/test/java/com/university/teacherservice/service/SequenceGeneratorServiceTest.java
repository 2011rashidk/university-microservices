package com.university.teacherservice.service;

import com.university.teacherservice.model.TeacherIdSequence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SequenceGeneratorServiceTest {

    @Mock
    private MongoOperations mongoOperations;

    @InjectMocks
    private SequenceGeneratorService sequenceGeneratorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateSequenceNumber() {
        TeacherIdSequence teacherIdSequence = new TeacherIdSequence();
        teacherIdSequence.setSequenceNumber(1);
        when(mongoOperations.findAndModify(
                any(Query.class),
                any(Update.class),
                any(FindAndModifyOptions.class),
                eq(TeacherIdSequence.class)
        )).thenReturn(teacherIdSequence);

        String result = sequenceGeneratorService.generateSequenceNumber();

        verify(mongoOperations, times(1))
                .findAndModify(
                        any(Query.class),
                        any(Update.class),
                        any(FindAndModifyOptions.class),
                        eq(TeacherIdSequence.class));
        assertNotNull(result);
        assertEquals("HT1", result);
    }
}