package com.example.QuoraReactiveApp.controllers;

import com.example.QuoraReactiveApp.dto.QuestionRequestDTO;
import com.example.QuoraReactiveApp.dto.QuestionResponseDTO;
import com.example.QuoraReactiveApp.services.IQuestionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final IQuestionService questionService;

    public QuestionController(IQuestionService _questionService) {
        this.questionService = _questionService;
    }

    @GetMapping
    public ResponseEntity<Flux<QuestionResponseDTO>> getAllQuestions(
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.status(200).body(questionService.getAllQuestions());
    }

    @PostMapping
    public Mono<QuestionResponseDTO> createQuestion(@Valid @RequestBody QuestionRequestDTO questionRequestDTO) {
        return questionService.createQuestion(questionRequestDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<QuestionResponseDTO>> getQuestionById(@PathVariable String id) {
        Mono<QuestionResponseDTO> fetchedQuestion = questionService.getQuestionById(id);
        return ResponseEntity.status(201).body(fetchedQuestion);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteQuestionById(@PathVariable String id) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @GetMapping("/search")
    public Flux<QuestionResponseDTO> searchQuestions(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size
    ) {
        return questionService.searchQuestions(query, page, size);
    }
}