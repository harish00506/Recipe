package com.recipeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Error response DTO with field validation errors
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponseWithFields {
    private int status;
    private String message;
    private LocalDateTime timestamp;
    private String path;
    private Map<String, String> fieldErrors;
}
