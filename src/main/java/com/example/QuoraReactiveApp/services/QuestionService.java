package com.example.QuoraReactiveApp.services;

import com.example.QuoraReactiveApp.adapter.QuestionAdapter;
import com.example.QuoraReactiveApp.dto.QuestionRequestDTO;
import com.example.QuoraReactiveApp.dto.QuestionResponseDTO;
import com.example.QuoraReactiveApp.models.Question;
import com.example.QuoraReactiveApp.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService {

    private final QuestionRepository questionRepository;

    @Override
    public Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO questionRequestDTO) {
        Question question = QuestionAdapter.toQuestionModel(questionRequestDTO);

//        Mono<Question> questionMono = questionRepository.save(question);
//        Mono<QuestionResponseDTO> questionResponseDTOMono = questionMono.map(QuestionAdapter::toQuestionResponseDTO);

        return questionRepository.save(question)
                .map(QuestionAdapter::toQuestionResponseDTO)
                .doOnSuccess(response -> System.out.println("Question Created Successfully: " + question))
                .doOnError(error -> System.out.println("Error creating Question " + error));
    }

    @Override
    public Flux<QuestionResponseDTO> getAllQuestions() {
        return questionRepository.findAll().map(QuestionAdapter::toQuestionResponseDTO);
    }

    @Override
    public Mono<QuestionResponseDTO> getQuestionById(String id) {
        return questionRepository.findById(id)
                .map(QuestionAdapter::toQuestionResponseDTO)
                .doOnSuccess(response -> System.out.println("Question fetched Successfully: " + response))
                .doOnError(error -> System.out.println("Error fetching Question: " + error));
    }

    @Override
    public Flux<QuestionResponseDTO> searchQuestions(String query, int offset, int page) {
        Pageable reqPage = PageRequest.of(offset, page);
        return questionRepository.findByTitleOrContentContainingIgnoreCase(query, reqPage)
                .map(QuestionAdapter::toQuestionResponseDTO)
                .doOnError(error -> System.out.println("No records found for the search term: " + query))
                .doOnComplete(() -> System.out.println("Questions fetched successfully."));
    }

    @Override
    public Mono<Void> deleteQuestionById(String id) {
        return null;
    }

}