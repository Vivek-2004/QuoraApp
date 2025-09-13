package com.example.QuoraReactiveApp.services;

import com.example.QuoraReactiveApp.dto.QuestionDeleteResponseDTO;
import com.example.QuoraReactiveApp.dto.QuestionRequestDTO;
import com.example.QuoraReactiveApp.dto.QuestionResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IQuestionService {
    public Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO questionRequestDTO);

    public Flux<QuestionResponseDTO> getAllQuestions(String cursor, int size);

    public Mono<QuestionResponseDTO> getQuestionById(String id);

    public Flux<QuestionResponseDTO> searchQuestions(String query, int offset, int page);

    public Mono<QuestionDeleteResponseDTO> deleteQuestionById(String id);
}