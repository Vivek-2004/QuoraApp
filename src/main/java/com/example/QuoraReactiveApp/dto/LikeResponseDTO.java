package com.example.QuoraReactiveApp.dto;

import java.time.LocalDateTime;

public class LikeResponseDTO {
    private String id;
    private String targetId;
    private String targetType;
    private Boolean isLike;
    private LocalDateTime createdAt;
}