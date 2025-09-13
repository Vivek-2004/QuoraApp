package com.example.QuoraReactiveApp.services;

import com.example.QuoraReactiveApp.dto.AnswerRequestDTO;
import com.example.QuoraReactiveApp.dto.AnswerResponseDTO;
import reactor.core.publisher.Mono;

public interface IAnswerService {
    public Mono<AnswerResponseDTO> createAnswer(AnswerRequestDTO answerRequestDTO);

    public Mono<AnswerResponseDTO> getAnswerById(String id);
}