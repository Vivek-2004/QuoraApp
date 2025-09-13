package com.example.QuoraReactiveApp.services;

import com.example.QuoraReactiveApp.adapter.QuestionAdapter;
import com.example.QuoraReactiveApp.dto.QuestionDeleteResponseDTO;
import com.example.QuoraReactiveApp.dto.QuestionRequestDTO;
import com.example.QuoraReactiveApp.dto.QuestionResponseDTO;
import com.example.QuoraReactiveApp.events.ViewCountEvent;
import com.example.QuoraReactiveApp.models.Question;
import com.example.QuoraReactiveApp.producers.KafkaEventProducer;
import com.example.QuoraReactiveApp.repositories.QuestionRepository;
import com.example.QuoraReactiveApp.utils.CursorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService {

    private final QuestionRepository questionRepository;
    private final KafkaEventProducer kafkaEventProducer;

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
    public Mono<QuestionResponseDTO> getQuestionById(String id) {
        return questionRepository.findById(id)
                .map(QuestionAdapter::toQuestionResponseDTO)
                .doOnError(error -> System.out.println("Error fetching Question: " + error))
                .doOnSuccess(response -> {
                    System.out.println("Question fetched Successfully: " + response);
                    ViewCountEvent viewCountEvent = ViewCountEvent.builder()
                            .targetId(id)
                            .targetType("question")
                            .timestamp(LocalDateTime.now())
                            .build();
                    kafkaEventProducer.publishViewCountEvent(viewCountEvent);
                });
    }

    @Override
    public Flux<QuestionResponseDTO> getAllQuestions(String cursor, int size) {
        Pageable reqPage = PageRequest.of(0, size);
        if (!CursorUtils.isValidCursor(cursor)) {
            return questionRepository.findTop10ByOrderByCreatedAtAsc()
                    .take(size)
                    .map(QuestionAdapter::toQuestionResponseDTO)
                    .doOnError(error -> System.out.println("Error fetching Questions: " + error))
                    .doOnComplete(() -> System.out.println("Top 10 Questions fetched Successfully."));
        } else {
            LocalDateTime cursorTimeStamp = CursorUtils.parseCursor(cursor);
            return questionRepository.findByCreatedAtGreaterThanOrderByCreatedAtAsc(cursorTimeStamp, reqPage)
                    .map(QuestionAdapter::toQuestionResponseDTO)
                    .doOnError(error -> System.out.println("Error fetching Questions: " + error))
                    .doOnComplete(() -> System.out.println("Questions fetched Successfully."));
        }
//        return questionRepository.findAll().map(QuestionAdapter::toQuestionResponseDTO);
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
    public Mono<QuestionDeleteResponseDTO> deleteQuestionById(String id) {
        Mono<QuestionDeleteResponseDTO> dto = questionRepository.existsById(id)
                .flatMap(exists -> exists
                        ?
                        questionRepository.deleteById(id).thenReturn(
                                QuestionDeleteResponseDTO
                                        .builder()
                                        .message("Question with ID : " + id + " Deleted Successfully.")
                                        .build()
                        )
                        :
                        Mono.just(
                                QuestionDeleteResponseDTO
                                        .builder()
                                        .message("Question with ID: " + id + " doesn't exist.")
                                        .build()
                        )
                );
        return dto;
    }

}