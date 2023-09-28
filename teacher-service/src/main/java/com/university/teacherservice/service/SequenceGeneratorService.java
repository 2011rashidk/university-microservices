package com.university.teacherservice.service;

import com.university.teacherservice.model.TeacherIdSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceGeneratorService {

    private final MongoOperations mongoOperations;

    @Autowired
    public SequenceGeneratorService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public String generateSequenceNumber() {
        TeacherIdSequence counter = mongoOperations.findAndModify(query(where("_id")),
                new Update().inc("sequenceNumber", 1), options().returnNew(true).upsert(true),
                TeacherIdSequence.class);
        int sequenceNumber = !Objects.isNull(counter) ? counter.getSequenceNumber() : 1;
        return "HT".concat(Integer.toString(sequenceNumber));
    }

}
