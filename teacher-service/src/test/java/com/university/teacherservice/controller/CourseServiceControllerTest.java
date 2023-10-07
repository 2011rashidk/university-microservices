package com.university.teacherservice.controller;

import com.university.teacherservice.client.CourseServiceClient;
import com.university.teacherservice.response.CourseResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.reactivestreams.Publisher;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static reactor.core.publisher.Mono.when;


class CourseServiceControllerTest {

    @InjectMocks
    CourseServiceController courseServiceController;

    @Mock
    CourseServiceClient courseServiceClient;

    @Test
    void testGetAvailableCourses() {

    }

}