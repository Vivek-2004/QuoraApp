package com.example.QuoraReactiveApp.consumers;

import com.example.QuoraReactiveApp.config.KafkaConfig;
import com.example.QuoraReactiveApp.events.ViewCountEvent;
import com.example.QuoraReactiveApp.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaEventConsumer {

    private final QuestionRepository questionRepository;

    @KafkaListener(
            topics = KafkaConfig.TOPIC_NAME,
            groupId = "view-count-consumer",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleViewCountEvent(ViewCountEvent viewCountEvent) {
        questionRepository.findById(viewCountEvent.getTargetId())
                .flatMap(question -> {
                    Integer views = question.getViews();
                    question.setViews(views == null ? 1 : views + 1);
                    return questionRepository.save(question);
                })
                .subscribe(updatedQuestion -> {
                    System.out.println("View Count incremented for question: " + viewCountEvent.getTargetId());
                }, error -> {
                    System.out.println("Error incrementing view count for: " + viewCountEvent.getTargetId());
                });
    }
}