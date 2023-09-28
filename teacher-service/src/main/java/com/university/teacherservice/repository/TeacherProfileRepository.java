package com.university.teacherservice.repository;

import com.university.teacherservice.model.TeacherProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeacherProfileRepository extends MongoRepository<TeacherProfile, String> {
}
