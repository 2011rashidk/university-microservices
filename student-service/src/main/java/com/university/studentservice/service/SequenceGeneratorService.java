package com.university.studentservice.service;

import com.university.studentservice.model.StudentIdSequence;
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
        StudentIdSequence counter = mongoOperations.findAndModify(query(where("_id")),
                new Update().inc("sequenceNumber", 1), options().returnNew(true).upsert(true),
                StudentIdSequence.class);
        int sequenceNumber = !Objects.isNull(counter) ? counter.getSequenceNumber() : 1;
        return "HS".concat(Integer.toString(sequenceNumber));
    }

}
