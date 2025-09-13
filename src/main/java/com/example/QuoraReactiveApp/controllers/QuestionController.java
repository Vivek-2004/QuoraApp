package com.example.QuoraReactiveApp.controllers;

import com.example.QuoraReactiveApp.dto.QuestionDeleteResponseDTO;
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
        Flux<QuestionResponseDTO> response = questionService.getAllQuestions(cursor, size)
                .doOnError(error -> System.out.println("An Error Occurred."))
                .doOnComplete(() -> System.out.println("Questions fetched Successfully."));
        return ResponseEntity.status(200).body(response);
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
    public Mono<QuestionDeleteResponseDTO> deleteQuestionById(@PathVariable String id) {
        return questionService.deleteQuestionById(id);
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