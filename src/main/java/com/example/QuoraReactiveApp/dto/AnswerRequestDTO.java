package com.example.QuoraReactiveApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerRequestDTO {

    @NotBlank(message = "Content is Required")
    @Size(min = 10, max = 10000, message = "Content must be between 10 and 10000 characters.")
    private String content;

    @NotBlank(message = "Question ID is Required.")
    private String questionId;

}