package com.example.QuoraReactiveApp.events;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViewCountEvent {
    private String targetId;
    private String targetType;
    private LocalDateTime timestamp;
}