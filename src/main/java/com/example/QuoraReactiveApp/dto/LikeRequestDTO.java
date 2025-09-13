package com.example.QuoraReactiveApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeRequestDTO {

    @NotBlank(message = "Target ID is Required.")
    private String targetId;

    @NotBlank(message = "Target Type is Required.")
    private String targetType;

    @NotNull(message = "isLike is Required.")
    private Boolean isLike;

}