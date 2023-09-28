package com.university.teacherservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document("teacher_id_sequence")
public class TeacherIdSequence {

    @Id
    private String id;

    private Integer sequenceNumber;
}
