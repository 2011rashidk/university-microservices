package com.university.studentservice.repository;

import com.university.studentservice.model.StudentProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentProfileRepository extends MongoRepository<StudentProfile, String> {
}
