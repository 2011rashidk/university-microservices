package com.university.studentservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document("student_id_sequence")
public class StudentIdSequence {

    @Id
    private String id;

    private Integer sequenceNumber;
}
