package com.example.QuoraReactiveApp.repositories;

import com.example.QuoraReactiveApp.models.Answer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends ReactiveMongoRepository<Answer, String> {

}